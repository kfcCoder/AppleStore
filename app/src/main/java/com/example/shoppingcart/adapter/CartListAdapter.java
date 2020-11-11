package com.example.shoppingcart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcart.R;
import com.example.shoppingcart.data.CartItem;
import com.example.shoppingcart.data.Product;
import com.example.shoppingcart.databinding.CartRowBinding;

public class CartListAdapter extends ListAdapter<CartItem, CartListAdapter.CartListViewHolder> {
    private CartRowBinding cartRowBinding;
    private CartInterface cartInterface;

    public CartListAdapter(CartInterface cartInterface) {
        super(CartItem.itemCallback);
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        cartRowBinding = DataBindingUtil.inflate(inflater, R.layout.cart_row, parent, false);



        return new CartListViewHolder(cartRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, final int position) {
        holder.cartRowBinding.setCartItem(getItem(position));
        holder.cartRowBinding.executePendingBindings();
    }

    //static class CartListViewHolder extends RecyclerView.ViewHolder {
    class CartListViewHolder extends RecyclerView.ViewHolder {
        CartRowBinding cartRowBinding;

        public CartListViewHolder(@NonNull CartRowBinding cartRowBinding) {
            super(cartRowBinding.getRoot());
            this.cartRowBinding = cartRowBinding;

            cartRowBinding.deleteProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartInterface.deleteItem(getItem(getAdapterPosition()));
                }
            });

            cartRowBinding.quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int quantity = position + 1; // position: spinner的位置
                    if(quantity == getItem(getAdapterPosition()).getQuantity()) {
                        return;
                    }

                    cartInterface.changeQuantity(getItem(getAdapterPosition()), quantity);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });





        }
    }

    /**
     * CartInterface
     */
    public interface CartInterface {
        public void deleteItem(CartItem cartItem);
        public void changeQuantity(CartItem cartItem, int quantity);
    }
}
