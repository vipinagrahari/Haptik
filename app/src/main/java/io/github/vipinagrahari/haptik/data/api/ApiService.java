package io.github.vipinagrahari.haptik.data.api;


import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by vipin on 8/11/16.
 */

public interface ApiService {

    String API_BASE_URL = "http://haptik.co/android/";
    @GET("test_data")
    Call<JsonObject> getMessages();
}
