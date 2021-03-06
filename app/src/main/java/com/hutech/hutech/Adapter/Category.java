package com.hutech.hutech.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hutech.hutech.Models.Categories;
import com.hutech.hutech.R;

import java.util.ArrayList;

import static com.hutech.hutech.Utils.Constants_urls.BaseAPI.IMG_URL;
/**
 * Created by Raghavendra on 6/3/2021.
 */
public class Category extends RecyclerView.Adapter<Category.ViewHolder> {

    private final Activity activity;
    private final LayoutInflater inflater;
    private ArrayList<Categories> categoryArrayList ;
    private OnAddClickListener onAddClickListener;
    public Category(Activity activity, ArrayList<Categories> categoryList) {
        this.activity = activity;
        this.categoryArrayList = categoryList;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface OnAddClickListener {
        void onAddClicked(int position,Categories categories);
    }

    public void setOnAddClickListener(OnAddClickListener listener) {
        this.onAddClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.categories, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Categories category = categoryArrayList.get(position);
        RequestOptions options = new RequestOptions()

                .transform( new RoundedCorners(15))
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(activity)
                .load(IMG_URL+category.image)

                .apply(options)
                .into(holder.ivCategory);

        holder.name.setText(category.name);

        holder.ivCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAddClickListener != null) {
                    onAddClickListener.onAddClicked(position,category);
                }
            }
        });
    }






    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategory;
        TextView name ;

        public ViewHolder(View itemView) {
            super(itemView);


            this.ivCategory = (ImageView) itemView.findViewById(R.id.iv_category);
            this.name = (TextView) itemView.findViewById(R.id.name);

        }
    }
}
