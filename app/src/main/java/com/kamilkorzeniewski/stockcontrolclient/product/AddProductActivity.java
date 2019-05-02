package com.kamilkorzeniewski.stockcontrolclient.product;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.kamilkorzeniewski.stockcontrolclient.R;
import com.kamilkorzeniewski.stockcontrolclient.retrofit.RestApiClient;

import androidx.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends Activity {

    private EditText productName;
    private EditText productQuantity;
    private EditText productPrice;
    private EditText productCode;

    private final RestApiClient restApiClient = RestApiClient.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productName = findViewById(R.id.product_name_input);
        productQuantity = findViewById(R.id.product_quantity_input);
        productPrice = findViewById(R.id.product_price_input);
        productCode = findViewById(R.id.product_code_input);

        String name = productName.getText().toString();
        int quantity = Integer.parseInt(productQuantity.getText().toString());
        float price = Float.parseFloat(productPrice.getText().toString());
        String code = productCode.getText().toString();

        Product product = new Product(null,name,quantity,code,price);

        findViewById(R.id.product_add_button).setOnClickListener(e -> {
            restApiClient.addProduct(product).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200) {
                        Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error ! - > "+ t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        });
        findViewById(R.id.product_cancel_button).setOnClickListener(e -> finish());

    }



}
