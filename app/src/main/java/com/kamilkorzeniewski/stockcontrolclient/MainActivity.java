package com.kamilkorzeniewski.stockcontrolclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.material.tabs.TabLayout;
import com.kamilkorzeniewski.stockcontrolclient.product.ProductsFragment;
import com.kamilkorzeniewski.stockcontrolclient.security.LoginActivity;

import java.util.logging.Logger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    private CustomPagerAdapter customPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;




    /*
        * First attach products fragment ( fetch data with products from database)
        * Then check if is logged in.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);

        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        customPagerAdapter.addFragment(new ProductsFragment().newInstance());
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(customPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        final String TOKEN_KEY = getString(R.string.token_key);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        /*
            * Redirect to Login Activity if have not token in storage.
         */

        if(!sharedPreferences.contains(TOKEN_KEY)){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setMessage("Are you sure to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> MainActivity.this.finish())
                .setNegativeButton("No",null)
                .show();
    }
}
