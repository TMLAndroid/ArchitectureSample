package com.demo.tangminglong.architecutresample.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.tangminglong.architecutresample.R;
import com.demo.tangminglong.architecutresample.databinding.ListFragmentBinding;
import com.demo.tangminglong.architecutresample.db.entity.ProductEntity;
import com.demo.tangminglong.architecutresample.model.Product;
import com.demo.tangminglong.architecutresample.viewmodel.ProductListViewModel;

import java.util.List;

/**
 * Created by tangminglong on 17/12/18.
 */

public class ProductListFragment extends Fragment{


    public static final String TAG = "ProductListViewModel";
    private ListFragmentBinding mBinding;
    private ProductAdapter mProductAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        mProductAdapter = new ProductAdapter(mProductClickCallBack);
        mBinding.productsList.setAdapter(mProductAdapter);
        return mBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductListViewModel viewModel = ViewModelProviders.of(this).get(ProductListViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(ProductListViewModel viewModel){
       viewModel.getProducts().observe(this, new Observer<List<ProductEntity>>() {
           @Override
           public void onChanged(@Nullable List<ProductEntity> productEntities) {
               if (productEntities != null){
                   mBinding.setIsLoading(false);
                   mProductAdapter.setProductList(productEntities);
               }else {
                   mBinding.setIsLoading(true);
               }

               mBinding.executePendingBindings();//更新绑定数据的UI
           }
       });
    }

    private final ProductClickCallBack mProductClickCallBack = new ProductClickCallBack() {
        @Override
        public void onClick(Product product) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
                ((MainActivity)getActivity()).show(product);
            }
        }
    };
}
