package com.kamilkorzeniewski.stockcontrolclient.Product;

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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Product> productList;
    private final Context context;

    private final ProductRestApiClient apiClient;
    ProductRecyclerViewAdapter(Context context, List<Product> initialData) {
        this.context = context;
        productList = initialData;
        apiClient = ProductRestApiClient.getInstance();
    }


    void addProduct(Product product) {
        productList.add(product);
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
                case MENU_EDIT:
                    Intent intent = new Intent(context,EditProductActivity.class);
                    intent.putExtra(EditProductActivity.PRODUCT_ID,productId.getText().toString());
                    context.startActivity(intent);
                    return true;
                case MENU_DELETE:
                    Long id = Long.parseLong(productId.getText().toString());
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
                    return true;
            }
            return false;
        };

    }

    private class TitleItem extends RecyclerView.ViewHolder {
        private static final int VIEW_TYPE = 1;

        public TitleItem(@NonNull View itemView) {
            super(itemView);
        }
    }
}
