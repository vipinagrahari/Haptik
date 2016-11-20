package io.github.vipinagrahari.haptik.data.api;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vipin on 18/11/16.
 */

public class ServiceGenerator {
    static ApiService apiService;
    private static OkHttpClient httpClient;


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiService.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


    /**
     *
     * @return This method return a Singleton of the ApiService
     *
     */
    public static ApiService getInstance() {
        if (apiService != null) {
            return apiService;
        }



        httpClient= new OkHttpClient.Builder()
                .build();

        Retrofit retrofit = builder.client(httpClient).build();
        apiService = retrofit.create(ApiService.class);
        return apiService;
    }
}
