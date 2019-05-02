package com.kamilkorzeniewski.stockcontrolclient.retrofit;

import com.kamilkorzeniewski.stockcontrolclient.product.Product;
import com.kamilkorzeniewski.stockcontrolclient.security.AuthModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("product/")
    Call<ResponseBody> postProduct(@Body Product product);

    @POST("login/")
    Call<String> login(@Body AuthModel authModel);
}
