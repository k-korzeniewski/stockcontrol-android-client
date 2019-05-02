package com.kamilkorzeniewski.stockcontrolclient.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kamilkorzeniewski.stockcontrolclient.R;
import com.kamilkorzeniewski.stockcontrolclient.retrofit.RestApiClient;

import androidx.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductActivity extends Activity {

    final static String PRODUCT_ID = "productId";
    private final static RestApiClient api = RestApiClient.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Intent intent = getIntent();
        Long id = intent.getLongExtra(PRODUCT_ID,-1L);

        TextView textView = findViewById(R.id.edit_product_id);
        EditText productName = findViewById(R.id.edit_product_name);
        EditText productQuantity = findViewById(R.id.edit_product_quantity);
        EditText productPrice = findViewById(R.id.edit_product_price);
        Button saveButton = findViewById(R.id.edit_product_save_button);
        Button cancelButton = findViewById(R.id.edit_product_cancel_button);

        api.getProduct(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                textView.setText(id.toString());
                productName.setText(product.name);
                productQuantity.setText(Integer.toString(product.quantity));
                productPrice.setText(Float.toString(product.price));
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG);
            }
        });

        saveButton.setOnClickListener(v -> {
            String name = productName.getText().toString();
            int quantity = Integer.parseInt(productQuantity.getText().toString());
            float price = Float.parseFloat(productPrice.getText().toString());
            Product product = new Product(id,name,quantity,"",price);
            api.putProduct(product,product.id).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getApplicationContext(),"Saved !",Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Failed !",Toast.LENGTH_LONG).show();
                }
            });

            finish();
        });

        cancelButton.setOnClickListener(v-> finish());

    }


}
