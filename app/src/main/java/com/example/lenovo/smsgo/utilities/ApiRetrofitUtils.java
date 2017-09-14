package com.example.lenovo.smsgo.utilities;

import com.example.lenovo.smsgo.ServicesRequest.BaseRetrofitClient;
import com.example.lenovo.smsgo.Interfaces.ApiService;

/**
 * Created by LENOVO on 7/09/2017.
 */

public class ApiRetrofitUtils {

    private ApiRetrofitUtils(){}
    public static final String BASE_URL = "http://10.100.107.156:8080/";

    public static ApiService getApiService(){
        return BaseRetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }

}
