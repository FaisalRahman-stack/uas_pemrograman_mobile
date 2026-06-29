package com.example.uas_mobile.network;

import com.example.uas_mobile.model.Endemik;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("endemik.json")
    Call<List<Endemik>> getEndemik();
}
