package com.hutech.hutech.Adapter;

import android.app.Activity;
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
import com.hutech.hutech.Models.CategoryListItem;

import com.hutech.hutech.R;

import java.util.ArrayList;
/**
 * Created by Raghavendra on 6/3/2021.
 */

public class NewArrivalAdapter extends RecyclerView.Adapter<NewArrivalAdapter.ViewHolder> {

    Activity activity;
    private ArrayList<CategoryListItem> categoryItemsList;
    private LayoutInflater inflater;
    View listItem;

    public NewArrivalAdapter(ArrayList<CategoryListItem> itemsList, FragmentActivity activity) {
        this.activity = activity;
        this.categoryItemsList = itemsList;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        listItem = layoutInflater.inflate(R.layout.new_arriavals, parent, false);
       ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CategoryListItem myListData = categoryItemsList.get(position);
        holder.name.setText(myListData.product_name);


        Glide.with(holder.imageView.getContext()).load(  myListData.product_image).into(holder.imageView);
        holder.rate.setText("₹" + myListData.price);



    }



    @Override
    public int getItemCount() {
        return categoryItemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name, rate;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            rate = itemView.findViewById(R.id.rate);


        }
    }
}
