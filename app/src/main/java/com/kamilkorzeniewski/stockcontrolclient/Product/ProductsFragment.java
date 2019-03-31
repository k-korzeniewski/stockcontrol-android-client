package com.kamilkorzeniewski.stockcontrolclient.Product;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kamilkorzeniewski.stockcontrolclient.CustomFragment;
import com.kamilkorzeniewski.stockcontrolclient.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragment extends CustomFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TITLE = "Products";
    private ProductRestApiClient productRestApiClient;
    private RecyclerView recyclerView;
    private ProductRecyclerViewAdapter recyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public CustomFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public String getPageTitle() {
        return TITLE;
    }


    @Override
    public void onCreate(Bundle saveInstanceState) {
        productRestApiClient = ProductRestApiClient.getInstance();
        recyclerViewAdapter = new ProductRecyclerViewAdapter(getContext(), new ArrayList<>());
        super.onCreate(saveInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(recyclerViewAdapter);
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        loadData();
        return view;
    }

    private void loadData() {
        productRestApiClient.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> products = response.body();
                recyclerViewAdapter.clearData();
                for (Product p : products) {
                    recyclerViewAdapter.addProduct(p);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                final String fetchErrorMessage = getResources().getString(R.string.fetch_error);
                t.printStackTrace();
                Toast.makeText(getContext(), fetchErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRefresh() {
        loadData();
        swipeRefreshLayout.setRefreshing(false);
    }
}
