package com.hutech.hutech.NetworkHelpers;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Raghavendra on 6/3/2021.
 */
public interface IResult {
    public void notifySuccess(int requestcode, String requestType, JSONObject response);
    public void notifyError(String requestType, VolleyError error);
}