package com.example.atg3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.SearchView;


import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.example.atg3.Api.ApiClient;
import com.example.atg3.Api.ApiInterface;
import com.example.atg3.RoomDatabase.Database;
import com.example.atg3.User.Foto;
import com.example.atg3.User.User;
import com.example.atg3.User.model;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<User> userList2;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    Adapter adapter;
    Database database;
    TaskModel modeViewModel;
    DrawerLayout drawerLayout;
    List<User> userList;
    int p=1;
    ActionBarDrawerToggle mToggle;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isscroll=false;
    int currentitems,totalitems,viewditems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        userList=new ArrayList<>();
        database= Room.databaseBuilder(getApplicationContext(),Database.class,"user")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        List<User> users=database.userDao().getAllUsers();
        adapter=new Adapter(MainActivity.this,users);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressBar=findViewById(R.id.progressbar);

        //tash=findViewById(R.id.tash);
      /*  TashieLoader tashie = new TashieLoader(
                this, 5,
                30, 10,
                ContextCompat.getColor(this, R.color.colorAccent));

        tashie.setAnimDuration(500);
        tashie.setAnimDelay(100);
        tashie.setInterpolator(new LinearInterpolator());*/


        //tash.addView(tashie);
        // LoadJson();
        //modeViewModel= ViewModelProviders.of(MainActivity.this).get(TaskModel.class);
       // userList=modeViewModel.getdata();
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            Log.i("internwt","connected");
        }
        else{
            connected = false;
            Log.i("internwt","not connected");

        }

        Log.i("main","prince");
        LoadJson();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isscroll=true;
                }


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentitems=layoutManager.getChildCount();
                totalitems=layoutManager.getItemCount();
                viewditems=layoutManager.findFirstVisibleItemPosition();
                if(isscroll&&(currentitems+viewditems==totalitems)){
                    isscroll=false;
                    LoadJson();

                }


            }
        });
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
        NavigationView navigationView=findViewById(R.id.navigationView);
        mToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public  void LoadJson(){
       // tash.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        String method="flickr.photos.getRecent",api_key="6f102c62f41998d151e5a1b48713cf13";
        String per_page="20",page=p+"",format="json",nojsoncallback="1",extras="url_s";
        model model=new model();

        Call<Foto> call;
        call = apiInterface.getFoto(method, per_page, page, api_key, format, nojsoncallback, extras);

        call.enqueue(new Callback<Foto>() {
            @Override
            public void onResponse(Call<Foto> call, Response<Foto> response) {
                if (response.isSuccessful() && response.body().getModel().getPhoto() != null) {

                    userList = response.body().getModel().getPhoto();
                    //userList.addAll(userList2);

                    adapter=new Adapter(MainActivity.this,userList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("prince", userList.size() + " "+p);
              //      tash.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.GONE);



                    p++;

                } else {
                    Log.i("prince", "error");
                    //   Toast.makeText(ModelRepocalss.this,"Loading rerro",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Foto> call, Throwable t) {

                Log.i("prince",t.getMessage());
            }
        });
    }
    public void fetchdata(){
        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        String method="flickr.photos.getRecent",api_key="6f102c62f41998d151e5a1b48713cf13";
        String per_page="20",page=p+"",format="json",nojsoncallback="1",extras="url_s";
        model model=new model();



        // Log.i("prince",userclasses.size()+" ");
        Call<Foto> call;
        call = apiInterface.getFoto(method, per_page, page, api_key, format, nojsoncallback, extras);

        call.enqueue(new Callback<Foto>() {
            @Override
            public void onResponse(Call<Foto> call, Response<Foto> response) {
                if (response.isSuccessful() && response.body().getModel().getPhoto() != null) {

                    userList = response.body().getModel().getPhoto();
                    adapter=new Adapter(MainActivity.this,userList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i("prince", userList.size() + " "+p);


                    p++;

                } else {
                    Log.i("prince", "error");
                    //   Toast.makeText(ModelRepocalss.this,"Loading rerro",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Foto> call, Throwable t) {


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem searchitem =menu.findItem(R.id.search);
        SearchView searchView=(SearchView) searchitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
}
