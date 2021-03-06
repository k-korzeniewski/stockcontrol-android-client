package com.kamilkorzeniewski.stockcontrolclient.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kamilkorzeniewski.stockcontrolclient.product.Product;
import com.kamilkorzeniewski.stockcontrolclient.security.AuthModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiClient {
    private RestApiService restApiService;
    private static RestApiClient instance;

    public static RestApiClient getInstance() {
        if (instance == null)
            instance = new RestApiClient();
        return instance;
    }

    private RestApiClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.19:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.build();
        restApiService = retrofit.create(RestApiService.class);
    }

    public Call<List<Product>> getAllProducts() {
        return restApiService.getAllProducts();
    }

    public Call<Void> removeProduct(Long productId) {
        return restApiService.removeProduct(productId);
    }

    public Call<Product> getProduct(Long productId) {
        return restApiService.getProductById(productId);
    }

    public Call<ResponseBody> putProduct(Product product, Long productId) {
        return restApiService.putProduct(product, productId);
    }

    public Call<ResponseBody> addProduct(Product product){
        return restApiService.postProduct(product);
    }

    public Call<String> login(AuthModel authModel){
        return restApiService.login(authModel);
    }
}
