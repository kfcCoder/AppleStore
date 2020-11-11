package com.example.shoppingcart.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.shoppingcart.R;
import com.example.shoppingcart.adapter.CartListAdapter;
import com.example.shoppingcart.data.CartItem;
import com.example.shoppingcart.databinding.FragmentCartBinding;
import com.example.shoppingcart.viewmodel.ShopViewModel;

import java.util.List;


public class CartFragment extends Fragment implements CartListAdapter.CartInterface {
    private ShopViewModel shopViewModel;
    private FragmentCartBinding fragmentCartBinding;
    private NavController navController;


    public CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        return fragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CartListAdapter cartListAdapter = new CartListAdapter(this);
        fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
        fragmentCartBinding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(),
                DividerItemDecoration.VERTICAL));

        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);

        shopViewModel.getCartItemsLive().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) { // 有新增物件進購物車就會觸發#onChanged()
                cartListAdapter.submitList(cartItems); // 觸發#onChanged()後就比較新/舊List, 確實不同就展示新List

                fragmentCartBinding.placeOrderButton.setEnabled(cartItems.size() > 0);
            }
        });

        /* replace with DataBinding
        shopViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                // ViewBinding
                fragmentCartBinding.orderTotalTextView.setText("Total: $" + integer);
            }
        });*/
        fragmentCartBinding.setShopViewModel(shopViewModel);
        fragmentCartBinding.setLifecycleOwner(getViewLifecycleOwner());

        fragmentCartBinding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_cartFragment_to_orderFragment);
            }
        });
    }

    @Override
    public void deleteItem(CartItem cartItem) {
        shopViewModel.removeItemFromCart(cartItem);
    }

    @Override
    public void changeQuantity(CartItem cartItem, int quantity) {
        shopViewModel.changeQuantity(cartItem, quantity);
    }
}