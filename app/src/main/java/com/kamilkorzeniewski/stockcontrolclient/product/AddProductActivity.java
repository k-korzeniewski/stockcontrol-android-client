package com.kamilkorzeniewski.stockcontrolclient.product;

import android.app.Activity;
import android.os.Bundle;

import com.kamilkorzeniewski.stockcontrolclient.R;

import androidx.annotation.Nullable;

public class AddProductActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
    }
}
