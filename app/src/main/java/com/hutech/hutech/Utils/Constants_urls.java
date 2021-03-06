package com.hutech.hutech.Utils;

/**
 * Created by Raghavendra on 6/3/2021.
 */
public interface Constants_urls {

    class BaseAPI {
        public static final String URL = "http://139.59.83.144:9050/api/";
        public static final String IMG_URL = "http://139.59.83.144:9050/";
    }

    class Service_Urls {
        public static final String GET_DATA = BaseAPI.URL + "home_test_section";
    }

    class WebServiceRequestCodes {
        public static final int get_data = 1;

    }


}
