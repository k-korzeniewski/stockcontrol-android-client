package com.kamilkorzeniewski.stockcontrolclient;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.kamilkorzeniewski.stockcontrolclient.Product.ProductsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    private CustomPagerAdapter customPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

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

    }
}
