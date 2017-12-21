package com.demo.tangminglong.architecutresample.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.demo.tangminglong.architecutresample.BasicApp;
import com.demo.tangminglong.architecutresample.DataRepository;
import com.demo.tangminglong.architecutresample.db.entity.CommentEntity;
import com.demo.tangminglong.architecutresample.db.entity.ProductEntity;

import java.util.List;

/**
 * Created by tangminglong on 17/12/20.
 */

public class ProductViewModel extends AndroidViewModel {

    private int mProductId;
    private LiveData<ProductEntity> mObservableProduct;
    private LiveData<List<CommentEntity>> mObservableComments;
    public ProductViewModel(@NonNull Application application, DataRepository dataRepository,int productId) {
        super(application);
        this.mProductId = productId;
        mObservableProduct = dataRepository.loadProduct(productId);
        mObservableComments = dataRepository.loadComments(productId);
    }

    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */
    public LiveData<List<CommentEntity>> getComments() {
        return mObservableComments;
    }

    public LiveData<ProductEntity> getObservableProduct() {
        return mObservableProduct;
    }

    public ObservableField<ProductEntity> product = new ObservableField<>();

    public void  setProduct(ProductEntity productEntity){
        this.product.set(productEntity);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        private Application mApplication;
        private DataRepository mRepository;
        private int mProductId;

        public Factory(Application application, int mProductId) {
            this.mApplication = application;
            this.mRepository = ((BasicApp) application).getDataRepository();
            this.mProductId = mProductId;
        }



        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProductViewModel(mApplication,mRepository,mProductId);
        }
    }
}
