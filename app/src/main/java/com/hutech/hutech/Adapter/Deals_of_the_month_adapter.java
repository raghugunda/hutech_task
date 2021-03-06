package com.hutech.hutech.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hutech.hutech.Models.CategoryListItem;
import com.hutech.hutech.R;

import java.util.ArrayList;
/**
 * Created by Raghavendra on 6/3/2021.
 */

public class Deals_of_the_month_adapter extends RecyclerView.Adapter<Deals_of_the_month_adapter.ViewHolder> {

    FragmentActivity activity;
    private ArrayList<CategoryListItem> categoryItemsList;
    private LayoutInflater inflater;
    View listItem;


    public Deals_of_the_month_adapter(ArrayList<CategoryListItem> itemsList, FragmentActivity activity) {
        this.activity = activity;
        this.categoryItemsList = itemsList;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    // RecyclerView recyclerView;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        listItem = layoutInflater.inflate(R.layout.deals_of_the_month, parent, false);
       ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CategoryListItem myListData = categoryItemsList.get(position);
        holder.name.setText(myListData.product_name);
        RequestOptions options = new RequestOptions()

                .transform( new RoundedCorners(15))
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(activity)
                .load(myListData.product_image)

                .apply(options)
                .into(holder.imageView);
        holder.rate.setText("₹" + myListData.price);
        holder.mrp.setText("₹" + myListData.mrp_price);
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


    }


    @Override
    public int getItemCount() {
        return categoryItemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name, rate, mrp;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            rate = itemView.findViewById(R.id.rate);
            mrp = itemView.findViewById(R.id.mrp);


        }
    }
}