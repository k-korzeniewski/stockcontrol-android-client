package com.kamilkorzeniewski.stockcontrolclient.Product;

import com.kamilkorzeniewski.stockcontrolclient.RestApiService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductRestApiClient {
    private RestApiService restApiService;
    private static ProductRestApiClient instance;

    static ProductRestApiClient getInstance() {
        if (instance == null)
            instance = new ProductRestApiClient();
        return instance;
    }

    private ProductRestApiClient() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.19:8080/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        restApiService = retrofit.create(RestApiService.class);
    }

    Call<List<Product>> getAllProducts() {
        return restApiService.getAllProducts();
    }
    Call<Void> removeProduct(Long productId){
        return restApiService.removeProduct(productId);
    }
    Call<Product> getProduct(Long productId){
        return restApiService.getProductById(productId);
    }

    Call<ResponseBody> putProduct(Product product, Long productId){
        return restApiService.putProduct(product,productId);
    }
}
