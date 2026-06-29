package com.example.uas_mobile.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.uas_mobile.database.AppDao;
import com.example.uas_mobile.database.AppDatabase;
import com.example.uas_mobile.model.Endemik;
import com.example.uas_mobile.model.Favorit;
import com.example.uas_mobile.network.RetrofitClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {
    private final AppDao appDao;
    private final ExecutorService executor;
    private final Application application;

    public AppRepository(Application application) {
        this.application = application;
        AppDatabase db = AppDatabase.getDatabase(application);
        appDao = db.appDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void fetchDataFromApi(final RepositoryCallback callback) {
        // Coba dari API dulu
        RetrofitClient.getApiService().getEndemik().enqueue(new Callback<List<Endemik>>() {
            @Override
            public void onResponse(Call<List<Endemik>> call, Response<List<Endemik>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    saveToDb(response.body(), callback);
                } else {
                    fetchFromAssets(callback);
                }
            }
            @Override
            public void onFailure(Call<List<Endemik>> call, Throwable t) {
                fetchFromAssets(callback);
            }
        });
    }

    private void fetchFromAssets(RepositoryCallback callback) {
        executor.execute(() -> {
            try {
                InputStream is = application.getAssets().open("endemik.json");
                Scanner s = new Scanner(is).useDelimiter("\\A");
                String json = s.hasNext() ? s.next() : "";
                List<Endemik> list = new Gson().fromJson(json, new TypeToken<List<Endemik>>(){}.getType());
                saveToDb(list, callback);
            } catch (Exception e) {
                callback.onError();
            }
        });
    }

    private void saveToDb(List<Endemik> list, RepositoryCallback callback) {
        executor.execute(() -> {
            appDao.deleteAllEndemik(); // Hapus data lama yang mungkin salah mapping
            appDao.insertEndemik(list);
            callback.onSuccess();
        });
    }

    public LiveData<List<Endemik>> getEndemikByKategori(String kategori) { return appDao.getEndemikByKategori(kategori); }
    public LiveData<List<Endemik>> getEndemikByAsal(String asal) { return appDao.getEndemikByAsal(asal); }
    public LiveData<List<Endemik>> getEndemikByKategoriAndAsal(String kategori, String asal) { return appDao.getEndemikByKategoriAndAsal(kategori, asal); }
    public LiveData<List<Endemik>> getAllEndemik() { return appDao.getAllEndemik(); }
    public LiveData<Endemik> getEndemikById(String id) { return appDao.getEndemikById(id); }
    public LiveData<List<Favorit>> getAllFavorit() { return appDao.getAllFavorit(); }
    public LiveData<Favorit> getFavoritById(String id) { return appDao.getFavoritById(id); }

    public void insertFavorit(Favorit favorit) { executor.execute(() -> appDao.insertFavorit(favorit)); }
    public void deleteFavorit(Favorit favorit) { executor.execute(() -> appDao.deleteFavorit(favorit)); }

    public interface RepositoryCallback {
        void onSuccess();
        void onError();
    }
}
