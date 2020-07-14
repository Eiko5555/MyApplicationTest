package com.example.myapplicationtest;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class ViewPagerAdapter extends PagerAdapter {
    String[] stringArray;
    private Context context;
    private LayoutInflater layoutInflater;

    public ViewPagerAdapter(Context context,
                            String[] stringArray) {
        this.context = context;
        this.stringArray = stringArray;
    }

    @Override
    public int getCount() {
        return stringArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView imageView = new ImageView(context);
        Glide.with(context)
                .load(stringArray[position])
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position,
                            @NonNull Object object) {
        container.removeView((View) object);
    }
}
