package com.kamilkorzeniewski.stockcontrolclient;

import com.kamilkorzeniewski.stockcontrolclient.Product.Product;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestApiService {
    @GET("product/{id}")
    Call<Product> getProductById(@Path("id") Long id);

    @GET("product/")
    Call<List<Product>> getAllProducts();

    @DELETE("product/{id}")
    Call<Void> removeProduct(@Path("id") Long id);

    @PUT("product/{id}")
    Call<ResponseBody> putProduct(@Body Product product, @Path("id")Long id);
}
