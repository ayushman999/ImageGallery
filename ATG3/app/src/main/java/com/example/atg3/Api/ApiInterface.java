package com.example.atg3.Api;

import com.example.atg3.User.Foto;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public abstract  interface ApiInterface {

@GET("rest")
    abstract Call<Foto> getFoto(

        @Query("method") String method,
        @Query("per_page") String per_page,
        @Query("page") String page,
        @Query("api_key") String api_key,
        @Query("format") String format,
        @Query("nojsoncallback") String nojsoncallback,
        @Query("extras") String extras
);


}
