package com.example.lenovo.smsgo.Interfaces;

import com.example.lenovo.smsgo.models.ModelSms;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by LENOVO on 5/09/2017.
 */

public interface ApiService {

    @GET("api/{value}")
    Call<List<ModelSms>> setGet(@Path("value") String value);

    @POST("api/message/sent")
    @FormUrlEncoded
    Call<ModelSms> setEmail(@Field("email") String mail);
}
