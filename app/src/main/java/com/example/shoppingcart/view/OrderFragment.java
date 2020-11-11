package com.example.shoppingcart.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcart.R;
import com.example.shoppingcart.databinding.FragmentCartBinding;
import com.example.shoppingcart.databinding.FragmentOrderBinding;
import com.example.shoppingcart.viewmodel.ShopViewModel;


public class OrderFragment extends Fragment {
    private FragmentOrderBinding fragmentOrderBinding;
    private NavController navController;
    private ShopViewModel shopViewModel;

    public OrderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentOrderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order,
                container, false);
        return fragmentOrderBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);

        fragmentOrderBinding.continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_orderFragment_to_shopFragment);

                shopViewModel.resetCart();
            }
        });
    }
}