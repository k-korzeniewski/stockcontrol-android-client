package com.kamilkorzeniewski.stockcontrolclient.product;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kamilkorzeniewski.stockcontrolclient.R;
import com.kamilkorzeniewski.stockcontrolclient.retrofit.RestApiClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Product> productList;
    private final Context context;

    private final RestApiClient apiClient;
    ProductRecyclerViewAdapter(Context context, List<Product> initialData) {
        this.context = context;
        productList = initialData;
        apiClient = RestApiClient.getInstance();
    }


    void addProducts(List<Product> products){
        productList.addAll(products);
        notifyDataSetChanged();
    }

    void updateProducts(List<Product> products){
        clearData();
        addProducts(products);
        notifyDataSetChanged();
    }

    void clearData() {
        productList.clear();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (i == TitleItem.VIEW_TYPE) {
            View view = inflater.inflate(R.layout.post_item_title, viewGroup, false);
            return new TitleItem(view);
        }
        View view = inflater.inflate(R.layout.post_item, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position == 0)
            return;
        int fixed_position = position - 1;
        Product product = productList.get(fixed_position);
        ItemHolder view = (ItemHolder) viewHolder;
        view.position = position;
        view.productName.setText(product.name);
        view.productId.setText(String.format("%s", product.id));
    }

    public int getItemViewType(int position) {
        if (position == 0)
            return TitleItem.VIEW_TYPE;
        return ItemHolder.VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return productList.size() + 1;
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private static final int VIEW_TYPE = 0;

        TextView productId;
        TextView productName;

        private static final int MENU_EDIT = 0;
        private static final int MENU_DELETE = 1;

        int position = -1;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            productId = itemView.findViewById(R.id.product_id);
            productName = itemView.findViewById(R.id.product_name);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(productName.getText());
            MenuItem edit = menu.add(0, MENU_EDIT, 1, "EDIT");
            MenuItem delete = menu.add(0, MENU_DELETE, 2, "DELETE");
            edit.setOnMenuItemClickListener(onMenuItemClickListener);
            delete.setOnMenuItemClickListener(onMenuItemClickListener);
        }


        private final MenuItem.OnMenuItemClickListener onMenuItemClickListener = item -> {
            switch (item.getItemId()) {
                case MENU_EDIT :{
                    String id = productId.getText().toString();
                    editMenuClickHandler(Long.parseLong(id));
                    return true;
                }
                case MENU_DELETE: {
                    String id = productId.getText().toString();
                    deleteMenuClickHandler(Long.parseLong(id));
                    return true;
                }
            }
            return false;
        };

        private void deleteMenuClickHandler(Long id){
            apiClient.removeProduct(id).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context,"REMOVED",Toast.LENGTH_LONG).show();
                    productList.remove(position - 1);
                    notifyItemRemoved(position);
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context,"SOMETHINK GONE WRONG",Toast.LENGTH_LONG).show();
                }
            });
        }

        private void editMenuClickHandler(Long id){
            Intent intent = new Intent(context,EditProductActivity.class);
            intent.putExtra(EditProductActivity.PRODUCT_ID,id);
            context.startActivity(intent);
        }

    }

    private class TitleItem extends RecyclerView.ViewHolder {
        private static final int VIEW_TYPE = 1;

        public TitleItem(@NonNull View itemView) {
            super(itemView);
        }
    }
}
