package com.example.atg3;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.atg3.Api.ApiClient;
import com.example.atg3.Api.ApiInterface;
import com.example.atg3.User.Foto;
import com.example.atg3.User.User;
import com.example.atg3.User.model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.content.ContextCompat.startActivity;

public class TaskModel extends ViewModel {

    List<User> users=new ArrayList<>();
    public ApiClient apiClient=new ApiClient();

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

                    users = response.body().getModel().getPhoto();

                    Log.i("prince", users.size() + " ");




                } else {
                    Log.i("prince", "error");
                    //   Toast.makeText(ModelRepocalss.this,"Loading rerro",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Foto> call, Throwable t) {


            }
        });
        return users;
    }
}
