package com.hutech.hutech.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.hutech.hutech.Models.BannerImage;
import com.hutech.hutech.R;

import java.util.ArrayList;

import static com.hutech.hutech.Utils.Constants_urls.BaseAPI.IMG_URL;
/**
 * Created by Raghavendra on 6/3/2021.
 */
public class BannerImageAdapter extends PagerAdapter {
    private Activity activity;
    private ArrayList<BannerImage> bannerImageArrayList;
    private LayoutInflater mLayoutInflater;


    public BannerImageAdapter(Activity context, ArrayList<BannerImage> imagesList){
        this.activity = context;
        bannerImageArrayList = imagesList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return bannerImageArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.banner_image, container, false);

        final BannerImage bannerImage = bannerImageArrayList.get(position);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);



        Glide.with(activity)
                .load(IMG_URL+bannerImage.bannerImage)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        container.addView(itemView);



        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
