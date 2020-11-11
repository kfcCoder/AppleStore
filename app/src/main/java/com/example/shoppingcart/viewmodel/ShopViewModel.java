package com.example.shoppingcart.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppingcart.data.CartItem;
import com.example.shoppingcart.data.Product;
import com.example.shoppingcart.repository.CartRepository;
import com.example.shoppingcart.repository.ShopRepository;

import java.util.List;

public class ShopViewModel extends ViewModel {
    /**
     * for DataBinding in fragment_product_detail.xml
     */
    private MutableLiveData<Product> productLive = new MutableLiveData<>();

    public void setProductLive(Product product) { // set at ShopFragment#onItemClick(Product product) callback
        productLive.setValue(product);
    }

    public LiveData<Product> getProductLive() {
        return productLive;
    }

    /**
     * from ShopRepository#MutableLiveData<List<Product>> productsLive
     */
    private ShopRepository shopRepository = new ShopRepository();

    public LiveData<List<Product>> getProductsLive() {
        return shopRepository.getProductsLive();
    }

    /**
     * from CartRepository#MutableLiveData<List<CartItem>> CartItemsLive
     */
    private CartRepository cartRepository = new CartRepository();

    public LiveData<List<CartItem>> getCartItemsLive() {
        return cartRepository.getCartItemsLive();
    }

    public boolean addItemToCart(Product product) {
        return cartRepository.addItemToCart(product);
    }

    public void removeItemFromCart(CartItem cartItem) {
        cartRepository.removeItemFromCart(cartItem);
    }

    public void changeQuantity(CartItem cartItem, int quantity) {
        cartRepository.changeQuantity(cartItem, quantity);
    }

    public LiveData<Integer> getTotalPrice() {
        return cartRepository.getTotalPrice();
    }

    public void resetCart() {
        cartRepository.initCart();
    }


}
