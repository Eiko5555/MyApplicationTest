package com.example.myapplicationtest;

import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Myadapter extends RecyclerView.Adapter {
    private List<Friend>petlist;
    private Context context;
    private LayoutInflater inflater;
    public Myadapter(Context context, List<Friend> petlist){
        this.context = context;
        this.petlist = petlist;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.recyclerview_item,
                parent, false);
        return new ItemHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Friend pets = petlist.get(position);
        String url = pets.getSmall();
        Glide.with(context).load(url).thumbnail(0.5f).circleCrop()
                .placeholder(R.drawable.no_image).
                into(((ItemHolder)holder).imageView);
    }

    @Override
    public int getItemCount() {
        return petlist.size();
    }
    public class ItemHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageforrecycler);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Detail.class);
                    intent.putExtra("id", petlist.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
