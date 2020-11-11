package com.example.shoppingcart.view;

import android.os.Bundle;
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
import com.example.shoppingcart.adapter.ShopListAdapter;
import com.example.shoppingcart.data.Product;
import com.example.shoppingcart.databinding.FragmentShopBinding;
import com.example.shoppingcart.viewmodel.ShopViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class ShopFragment extends Fragment implements ShopListAdapter.ShopInterface{
    private FragmentShopBinding fragmentShopBinding;
    private ShopListAdapter mShopListAdapter;
    private ShopViewModel shopViewModel;
    private NavController navController;

    public ShopFragment() {
    }

    /**
     * 1. init FragmentShopBinding
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentShopBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop, container, false);

        return fragmentShopBinding.getRoot();
    }

    /**
     * 1. init adapter
     * 2. init ShopViewModel
     * 3. navigate to ProductDetailFragment
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);

        mShopListAdapter = new ShopListAdapter(this);
        fragmentShopBinding.shopRecyclerview.setAdapter(mShopListAdapter);
        fragmentShopBinding.shopRecyclerview.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        fragmentShopBinding.shopRecyclerview.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.HORIZONTAL));

        shopViewModel.getProductsLive().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mShopListAdapter.submitList(products);
            }
        });

        navController = Navigation.findNavController(fragmentShopBinding.getRoot());

        fragmentShopBinding.setLifecycleOwner(getViewLifecycleOwner());
    }

    /**
     * 1. shop_row: ()->shopInterface.addItem(product), 有log
     * 2. fragment_product_detail: ()->shopViewModel.addItemToCart(shopViewModel.productLive), 沒log
     */
    @Override
    public void addItem(Product product) { // vs ShopViewModel#addItemToCart(Product product) ??
        boolean isAdded = shopViewModel.addItemToCart(product);
        if(isAdded) {
            Snackbar.make(requireView(), product.getName() + "added to cart", Snackbar.LENGTH_SHORT)
                    .setAction("Checkout", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navController.navigate(R.id.action_shopFragment_to_cartFragment);
                        }
                    })
                    .show();
        } else {
            Snackbar.make(requireView(), "Already has the max quantity in cart", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Product product) {
        shopViewModel.setProductLive(product);
        navController.navigate(R.id.action_shopFragment_to_productDetailFragment);
    }
}