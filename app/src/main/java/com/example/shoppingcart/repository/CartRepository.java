package com.example.shoppingcart.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingcart.data.CartItem;
import com.example.shoppingcart.data.Product;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {
    private MutableLiveData<List<CartItem>> cartItemsLive = new MutableLiveData<>();
    private MutableLiveData<Integer> totalPriceLive = new MutableLiveData<>();


    public CartRepository() {
        initCart();
        initPriceLive();
    }

    public void initCart() {
        cartItemsLive.setValue(new ArrayList<CartItem>());
        calculateTotalPrice();
    }

    private void initPriceLive() {
        totalPriceLive.setValue(0); // 沒加這句null check會擋掉
    }
    
    public LiveData<List<CartItem>> getCartItemsLive() {
        return cartItemsLive;
    }

    public boolean addItemToCart(Product product) {
        if(cartItemsLive.getValue() == null) { // null check
            initCart();
        }

        List<CartItem> cartItemList = new ArrayList<>(cartItemsLive.getValue());

        for(CartItem item : cartItemList) {
            if(item.getProduct().getId().equals(product.getId())) {
                if(item.getQuantity() == 5) {
                    return false;
                }

                int index = cartItemList.indexOf(item);
                item.setQuantity(item.getQuantity() + 1);
                cartItemList.set(index, item);
                cartItemsLive.setValue(cartItemList);
                calculateTotalPrice();
                return  true;
            }
        }

        CartItem cartItem = new CartItem(product, 1);
        cartItemList.add(cartItem);
        cartItemsLive.setValue(cartItemList);
        calculateTotalPrice();

        return true;
    }

    public void removeItemFromCart(CartItem cartItem) {
         if(cartItemsLive.getValue() == null) {
             return;
         }

         List<CartItem> cartItemsList = new ArrayList<>(cartItemsLive.getValue());
         cartItemsList.remove(cartItem);
         cartItemsLive.setValue(cartItemsList);

         calculateTotalPrice();
    }

    public void changeQuantity(CartItem cartItem, int quantity) {
        if(cartItemsLive.getValue() == null) return;

        List<CartItem> cartItemList = new ArrayList<>(cartItemsLive.getValue());
        CartItem updatedItem = new CartItem(cartItem.getProduct(),  quantity);
        cartItemList.set(cartItemList.indexOf(cartItem), updatedItem);
        cartItemsLive.setValue(cartItemList);

        calculateTotalPrice();
    }

    public LiveData<Integer> getTotalPrice() {
        if(totalPriceLive.getValue() == null) {
            totalPriceLive.setValue(0);
        }

        return totalPriceLive;
    }

    private void calculateTotalPrice() { // should be invoked every time setValue on cartItemsLive
        if(totalPriceLive.getValue() == null) return;

        int total = 0;
        List<CartItem> cartItemList = cartItemsLive.getValue();
        for(CartItem item : cartItemList) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        totalPriceLive.setValue(total);
    }

}
