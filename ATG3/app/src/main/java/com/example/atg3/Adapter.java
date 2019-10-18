package com.example.atg3;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.atg3.RoomDatabase.Database;
import com.example.atg3.User.User;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
    public Context context;
    public List<User> photo;
    public List<User> photfulllist;


    public Adapter(Context context, List<User> photo) {
        this.context = context;
        this.photo = photo;
        photfulllist=new ArrayList<>(photo);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ViewHolder viewHolder=holder;
        User user=photo.get(position);
        Database database= Room.databaseBuilder(context, Database.class,"user")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        database.userDao().insert(new User(user.getTitle(),user.getUrl_s()));
        RequestOptions requestOptions=new RequestOptions();
        Glide.with(context)
                .load(user.getUrl_s())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        Log.i("requse","failed");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        Log.i("request","pass");
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);
        holder.textView.setText(user.getTitle());


    }

    @Override
    public int getItemCount() {
        return photo.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<User> filterlist=new ArrayList<>();
            if(constraint==null||constraint.length()==0){
                filterlist.addAll(photfulllist);
            }
            else{
                String filt=constraint.toString().toLowerCase().trim();
                for(User user:photfulllist){
                    if(user.getTitle().toLowerCase().contains(filt)){
                        filterlist.add(user);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            photo.clear();
            photo.addAll((Collection<? extends User>) results.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.photo);
            textView=itemView.findViewById(R.id.title);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
