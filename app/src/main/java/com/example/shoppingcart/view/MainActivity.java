package com.example.shoppingcart.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.shoppingcart.R;
import com.example.shoppingcart.data.CartItem;
import com.example.shoppingcart.data.Product;
import com.example.shoppingcart.viewmodel.ShopViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NavController controller;
    private ShopViewModel shopViewModel;
    private int cartQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, controller);

        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        shopViewModel.getCartItemsLive().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                int quantity = 0;
                for(CartItem item : cartItems) {
                    quantity += item.getQuantity();
                }
                cartQuantity = quantity;
                invalidateOptionsMenu();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        controller.navigateUp(); //
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // 購物車選項
        final MenuItem menuItem = menu.findItem(R.id.cartFragment);

        // 購物車圖標
        View actionView = menuItem.getActionView(); // need to use app: prefix instead of android:(will return null)
        TextView cartBadgeTextView = actionView.findViewById(R.id.cart_badge_textview);
        cartBadgeTextView.setText(String.valueOf(cartQuantity));
        cartBadgeTextView.setVisibility(cartQuantity == 0 ? View.GONE : View.VISIBLE);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // 圖標的id要和欲跳轉的fragment id一樣
        return NavigationUI.onNavDestinationSelected(item, controller) // returns boolean already
            || super.onOptionsItemSelected(item); // if event isn't handled by NavigationUI, then use super call
    }
}