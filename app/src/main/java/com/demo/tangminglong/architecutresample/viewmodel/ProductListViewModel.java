package com.demo.tangminglong.architecutresample.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.demo.tangminglong.architecutresample.BasicApp;
import com.demo.tangminglong.architecutresample.db.entity.ProductEntity;

import java.util.List;

/**
 * Created by tangminglong on 17/12/20.
 */

public class ProductListViewModel extends AndroidViewModel{
    private final MediatorLiveData<List<ProductEntity>> mObservableProducts;

    public ProductListViewModel(@NonNull Application application) {
        super(application);
        mObservableProducts = new MediatorLiveData<>();
        mObservableProducts.setValue(null);//一开始设置空值 现在正在加载中
        LiveData<List<ProductEntity>> products = ((BasicApp) application).getDataRepository()
                .getProducts();
        mObservableProducts.addSource(products, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                mObservableProducts.setValue(productEntities);
            }
        });
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }
}
