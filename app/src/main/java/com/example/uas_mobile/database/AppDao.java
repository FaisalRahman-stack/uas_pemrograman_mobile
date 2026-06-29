package com.example.uas_mobile.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.uas_mobile.model.Endemik;
import com.example.uas_mobile.model.Favorit;

import java.util.List;

@Dao
public interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEndemik(List<Endemik> endemikList);

    @Query("SELECT * FROM endemik")
    LiveData<List<Endemik>> getAllEndemik();

    @Query("SELECT * FROM endemik WHERE id = :id")
    LiveData<Endemik> getEndemikById(String id);

    @Query("SELECT * FROM endemik WHERE kategori = :kategori")
    LiveData<List<Endemik>> getEndemikByKategori(String kategori);

    @Query("SELECT * FROM endemik WHERE asal LIKE '%' || :asal || '%'")
    LiveData<List<Endemik>> getEndemikByAsal(String asal);

    @Query("SELECT * FROM endemik WHERE kategori = :kategori AND asal LIKE '%' || :asal || '%'")
    LiveData<List<Endemik>> getEndemikByKategoriAndAsal(String kategori, String asal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorit(Favorit favorit);

    @Delete
    void deleteFavorit(Favorit favorit);

    @Query("SELECT * FROM favorit")
    LiveData<List<Favorit>> getAllFavorit();

    @Query("SELECT * FROM favorit WHERE id = :id")
    LiveData<Favorit> getFavoritById(String id);

    @Query("DELETE FROM endemik")
    void deleteAllEndemik();
}
