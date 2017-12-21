package com.demo.tangminglong.architecutresample;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.demo.tangminglong.architecutresample.db.AppDatabase;
import com.demo.tangminglong.architecutresample.db.entity.CommentEntity;
import com.demo.tangminglong.architecutresample.db.entity.ProductEntity;

import java.util.List;

/**
 * Created by tangminglong on 17/12/20.
 */

public class DataRepository {
    private static DataRepository sInstance;

    private AppDatabase mDatabase;

    private MediatorLiveData<List<ProductEntity>> mObservableProducts;

    public DataRepository(AppDatabase mDatabase) {
        this.mDatabase = mDatabase;
        mObservableProducts = new MediatorLiveData<>();
        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(), new Observer<List<ProductEntity>>() {

            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                if (mDatabase.getDatabaseCreated().getValue() != null){
                    mObservableProducts.postValue(productEntities);
                }
            }
        });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }

    public LiveData<ProductEntity> loadProduct(final int productId) {
        return mDatabase.productDao().loadProduct(productId);
    }

    public LiveData<List<CommentEntity>> loadComments(final int productId) {
        return mDatabase.commentDao().loadComments(productId);
    }
}
