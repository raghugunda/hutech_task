package com.hutech.hutech.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hutech.hutech.Adapter.BannerImageAdapter;
import com.hutech.hutech.Adapter.Category;
import com.hutech.hutech.Adapter.Deals_of_the_month_adapter;
import com.hutech.hutech.Adapter.NewArrivalAdapter;
import com.hutech.hutech.Helpers.SpacesItemDecoration;
import com.hutech.hutech.Models.BannerImage;
import com.hutech.hutech.Models.Categories;
import com.hutech.hutech.Models.CategoryListItem;
import com.hutech.hutech.NetworkHelpers.IResult;
import com.hutech.hutech.NetworkHelpers.VolleyService;
import com.hutech.hutech.R;
import com.hutech.hutech.Utils.Constants_urls;
import com.hutech.hutech.Utils.OFC_Utils;
import com.hutech.hutech.base.OFC_BaseActivity;
import com.hutech.hutech.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Raghavendra on 6/3/2021.
 */
public class MainActivity extends OFC_BaseActivity {

    ActivityMainBinding activityMainBinding;
    private ArrayList<CategoryListItem> dealsOfTheMonthListItems = new ArrayList<CategoryListItem>();
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    private ArrayList<BannerImage> bannerImageArrayList = new ArrayList<>();
    private ArrayList<BannerImage> category_abpoveArrayList = new ArrayList<>();

    private ArrayList<Categories> categoriesList = new ArrayList<>();

    private Category categoryAdapter;
    private BannerImageAdapter imageAdapter1, category_abpove, category_abpove1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        api_response();

        //These data are not there in API. so statically added
        String product_image[] = {
                "https://www.theguardian.pe.ca/media/photologue/photos/cache/Screen_Shot_2020-08-04_at_4.30.33_PM_large.png",
                "https://www.irishtimes.com/polopoly_fs/1.3594671.1534163385!/image/image.jpg_gen/derivatives/box_620_330/image.jpg",
                "https://www.thermofisher.com/blog/wp-content/uploads/2014/08/tomatoes.jpg",
                "https://thefamilydinnerproject.org/wp-content/uploads/2013/09/Green-bean-lime-633x326.jpg",
                "https://www.shethepeople.tv/wp-content/uploads/2019/05/cucumber-e1558166231577.jpg"
        };

        String product_name[] = {"Onion", "Potato", "Tomato", "Beans", "Cucumber"};
        String product_price[] = {"45", "60", "54", "35", "37"};
        String product_mrp[] = {"55", "75", "60", "45", "52"};

        for (int i = 0, len = product_image.length; i < len; i++) {
            CategoryListItem allCategoryModel = new CategoryListItem();
            allCategoryModel.product_image = product_image[i];
            allCategoryModel.product_name = product_name[i];

            allCategoryModel.price = product_price[i];
            allCategoryModel.mrp_price = product_mrp[i];


            dealsOfTheMonthListItems.add(allCategoryModel);

        }

        activityMainBinding.dealsOfTheMonth.addItemDecoration(new SpacesItemDecoration(10));
        Deals_of_the_month_adapter adapter1 = new Deals_of_the_month_adapter(dealsOfTheMonthListItems, MainActivity.this);
        activityMainBinding.dealsOfTheMonth.setHasFixedSize(true);
        activityMainBinding.dealsOfTheMonth.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        activityMainBinding.dealsOfTheMonth.setAdapter(adapter1);

        activityMainBinding.offerSales.addItemDecoration(new SpacesItemDecoration(10));
        NewArrivalAdapter newArrivalAdapter = new NewArrivalAdapter(dealsOfTheMonthListItems, MainActivity.this);
        activityMainBinding.offerSales.setHasFixedSize(true);
        activityMainBinding.offerSales.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        activityMainBinding.offerSales.setAdapter(newArrivalAdapter);

        //static data end

        imageAdapter1 = new BannerImageAdapter(this, bannerImageArrayList);
        activityMainBinding.bannerImageViewpager.setAdapter(imageAdapter1);

        category_abpove = new BannerImageAdapter(this, category_abpoveArrayList);
        activityMainBinding.bannerImageAboveCategory.setAdapter(category_abpove);
        category_abpove1 = new BannerImageAdapter(this, category_abpoveArrayList);
        activityMainBinding.bannerImageViewpager1.setAdapter(category_abpove1);


        categoryAdapter = new Category(this, categoriesList);
        activityMainBinding.gridCategory.setLayoutManager(new GridLayoutManager(this, 3));
        activityMainBinding.gridCategory.setAdapter(categoryAdapter);

        get_data();


    }

    private void get_data() {
        if (OFC_Utils.isOnline(this)) {
            OFC_Utils.showProgressDialog(this, "", "Loading...Please Wait..");
            JSONObject sendObj = new JSONObject();
            mVolleyService.postDataVolley(WebServiceRequestCodes.get_data, "POSTCALL", Service_Urls.GET_DATA, sendObj);
        } else {
            OFC_Utils.showNoNetworkAlert(this, false);
        }
    }

    private void api_response() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(int requestcode, String requestType, JSONObject response) {
                Log.d("response", String.valueOf(response));
                Log.d("response1", requestType);
                try {
                    OFC_Utils.hideProgressDialog();
                    if (requestcode == WebServiceRequestCodes.get_data) {
                        JSONObject mainJsonobject = new JSONObject(response.toString());
                        String responseType = mainJsonobject.optString("success");
                        if (responseType.trim().equalsIgnoreCase("true")) {
                            //JSONArray jsonArray = mainJsonobject.optJSONArray("components");
                            JSONArray jsonArray = new JSONArray(mainJsonobject.optString("components"));

                            if (jsonArray.length() > 0) {
                                for (int i = 0, len = jsonArray.length(); i < len; i++) {
                                    JSONObject components = jsonArray.getJSONObject(i);

                                    if (i == 0) {
                                        JSONArray banner_array = new JSONArray(components.optString("StaticBanner"));

                                        for (int k = 0; k < banner_array.length(); k++) {
                                            JSONObject itemObj = banner_array.getJSONObject(k);
                                            Log.v("OrderJsonArray ::: ", "" + itemObj);
                                            BannerImage allCategoryModel = new BannerImage();
                                            allCategoryModel.bannerImage = itemObj.optString("banner_image");
                                            allCategoryModel.name = itemObj.optString("banner_name");
                                            allCategoryModel.bannerId = Integer.parseInt(itemObj.optString("banner_id"));
                                            allCategoryModel.bcategory = itemObj.optString("url_type");
                                            bannerImageArrayList.add(allCategoryModel);
                                        }
                                        imageAdapter1.notifyDataSetChanged();
                                    } else if (i == 1) {
                                        Log.d("comp_name", components.optString("name"));
                                        JSONArray category_jsonArray = new JSONArray(components.optString("categorydata"));


                                        for (int j = 0; j < category_jsonArray.length(); j++) {
                                            JSONObject itemObj = category_jsonArray.getJSONObject(j);
                                            Log.v("OrderJsonArray ::: ", "" + itemObj);
                                            Categories allCategoryModel = new Categories();
                                            allCategoryModel.image = itemObj.optString("category_picture");
                                            allCategoryModel.name = itemObj.optString("category_name");
                                            allCategoryModel.id = Integer.parseInt(itemObj.optString("category_id"));

                                            categoriesList.add(allCategoryModel);
                                        }


                                        categoryAdapter.notifyDataSetChanged();
                                    } else if (i == 2) {
                                        JSONArray banner_array = new JSONArray(components.optString("AdsBanner"));

                                        for (int k = 0; k < banner_array.length(); k++) {
                                            JSONObject itemObj = banner_array.getJSONObject(k);
                                            BannerImage allCategoryModel = new BannerImage();
                                            allCategoryModel.bannerImage = itemObj.optString("banner_image");
                                            allCategoryModel.name = itemObj.optString("banner_name");
                                            allCategoryModel.bannerId = Integer.parseInt(itemObj.optString("banner_id"));
                                            allCategoryModel.bcategory = itemObj.optString("url_type");
                                            category_abpoveArrayList.add(allCategoryModel);
                                        }
                                        category_abpove.notifyDataSetChanged();
                                        category_abpove1.notifyDataSetChanged();
                                    }


                                }


                            }


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                OFC_Utils.hideProgressDialog();
                Log.d("response1", "Internal Server Error");
            }
        };
        mVolleyService = new VolleyService(mResultCallback, this);
    }
}