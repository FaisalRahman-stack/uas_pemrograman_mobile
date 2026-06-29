package com.example.uas_mobile.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.uas_mobile.model.Endemik;
import com.example.uas_mobile.model.Favorit;
import com.example.uas_mobile.repository.AppRepository;
import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private final AppRepository repository;

    public AppViewModel(Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public void fetchData(AppRepository.RepositoryCallback callback) { repository.fetchDataFromApi(callback); }
    public LiveData<List<Endemik>> getEndemikByKategori(String kategori) { return repository.getEndemikByKategori(kategori); }
    public LiveData<List<Endemik>> getEndemikByAsal(String asal) { return repository.getEndemikByAsal(asal); }
    public LiveData<List<Endemik>> getEndemikByKategoriAndAsal(String kategori, String asal) { return repository.getEndemikByKategoriAndAsal(kategori, asal); }
    public LiveData<List<Endemik>> getAllEndemik() { return repository.getAllEndemik(); }
    public LiveData<Endemik> getEndemikById(String id) { return repository.getEndemikById(id); }
    public LiveData<List<Favorit>> getAllFavorit() { return repository.getAllFavorit(); }
    public LiveData<Favorit> getFavoritById(String id) { return repository.getFavoritById(id); }
    public void toggleFavorit(Endemik endemik, boolean isFavorite) {
        Favorit fav = new Favorit(endemik.getId(), endemik.getNama(), endemik.getKategori(), endemik.getGambar(), endemik.getDeskripsi());
        if (isFavorite) repository.deleteFavorit(fav);
        else repository.insertFavorit(fav);
    }
}
