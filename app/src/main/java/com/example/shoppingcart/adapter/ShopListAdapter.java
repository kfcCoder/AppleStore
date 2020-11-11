package com.example.shoppingcart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcart.R;
import com.example.shoppingcart.data.Product;
import com.example.shoppingcart.databinding.ShopRowBinding;

public class ShopListAdapter extends ListAdapter<Product, ShopListAdapter.ShopListViewHolder> {
    private ShopRowBinding shopRowBinding;

    private ShopInterface shopInterface;

    /**
     * implement DiffUtil.ItemCallback<Product> to pass in super() call
     */
    public ShopListAdapter(ShopInterface shopInterface) {
        super(new DiffUtil.ItemCallback<Product>() {
            @Override
            public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                return (oldItem.getName().equals(newItem.getName())
                        && oldItem.getPrice() == newItem.getPrice()
                        && oldItem.isAvailable() == newItem.isAvailable()
                        && oldItem.getImageUrl().equals(newItem.getImageUrl()));
            }
        });

        this.shopInterface = shopInterface; // to perform callback functionality
    }

    /**
     * 1. init ShopRowBinding from ViewHolder
     * 2. bind shopInterface to shop_row xml
     */
    @NonNull
    @Override
    public ShopListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shop_row, parent, false);
        ShopListViewHolder holder = new ShopListViewHolder(view);
        shopRowBinding = holder.getShopRowBinding();
        shopRowBinding.setShopInterface(shopInterface); // perform onClick() callback in root layout of shop_row.xml
        return holder;
    }

    /**
     * Bind correct product to shopRowBinding
     */
    @Override
    public void onBindViewHolder(@NonNull ShopListViewHolder holder, int position) {
        Product product = getItem(position);
        holder.shopRowBinding.setProduct(product);
        holder.shopRowBinding.executePendingBindings();
    }


    /**
     * ViewHolder to retain a view and to get binding
     */
    static class ShopListViewHolder extends RecyclerView.ViewHolder {
        ShopRowBinding shopRowBinding;

        public ShopListViewHolder(@NonNull View itemView) {
            super(itemView);
            shopRowBinding = DataBindingUtil.bind(itemView);
        }

        public ShopRowBinding getShopRowBinding() {
            return shopRowBinding;
        }
    }

    /**
     * ShopInterface
     */
    public interface ShopInterface {
        public void addItem(Product product);
        public void onItemClick(Product product);
    }



}
