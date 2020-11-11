package com.example.shoppingcart.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppingcart.R;
import com.example.shoppingcart.databinding.FragmentProductDetailBinding;
import com.example.shoppingcart.viewmodel.ShopViewModel;


public class ProductDetailFragment extends Fragment {
    private FragmentProductDetailBinding fragmentProductDetailBinding;
    private ShopViewModel shopViewModel;

    public ProductDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProductDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
        return fragmentProductDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        fragmentProductDetailBinding.setShopViewModel(shopViewModel);
    }
}