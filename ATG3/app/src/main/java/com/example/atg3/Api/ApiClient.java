package com.example.atg3.Api;

import android.util.Log;

import com.example.atg3.User.Foto;
import com.example.atg3.User.User;
import com.example.atg3.User.model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String url="https://api.flickr.com/services/";
    public static Retrofit retrofit;
    public List<User> userList=new ArrayList<>();
    public static Retrofit getApiClient(){
        Gson gson=new GsonBuilder().setLenient().create();
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(url)
                    .client(getUnsafeOkHttpClient().build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;

    }
    public static OkHttpClient.Builder getUnsafeOkHttpClient(){

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<User> getdata(){
        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        String method="flickr.photos.getRecent",api_key="6f102c62f41998d151e5a1b48713cf13";
        String per_page="20",page="1",format="json",nojsoncallback="1",extras="url_s";
        model model=new model();



        // Log.i("prince",userclasses.size()+" ");
        Call<Foto> call;
        call = apiInterface.getFoto(method, per_page, page, api_key, format, nojsoncallback, extras);

        call.enqueue(new Callback<Foto>() {
            @Override
            public void onResponse(Call<Foto> call, Response<Foto> response) {
                if (response.isSuccessful() && response.body().getModel().getPhoto() != null) {

                    userList = response.body().getModel().getPhoto();
                    Log.i("prince", userList.size() + " ");




                } else {
                    Log.i("prince", "error");
                    //   Toast.makeText(ModelRepocalss.this,"Loading rerro",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Foto> call, Throwable t) {


            }
        });
        Log.i("main",userList.size()+" ");
        return userList;
    }
}
