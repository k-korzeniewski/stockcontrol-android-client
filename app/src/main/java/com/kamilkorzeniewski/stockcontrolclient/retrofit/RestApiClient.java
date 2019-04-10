package com.kamilkorzeniewski.stockcontrolclient.retrofit;

import com.kamilkorzeniewski.stockcontrolclient.product.Product;

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
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.19:8080/")
                .addConverterFactory(GsonConverterFactory.create());

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
}
