package com.demo.tangminglong.architecutresample.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.tangminglong.architecutresample.R;
import com.demo.tangminglong.architecutresample.databinding.CommentItemBinding;
import com.demo.tangminglong.architecutresample.databinding.ProductFragmentBinding;
import com.demo.tangminglong.architecutresample.db.entity.CommentEntity;
import com.demo.tangminglong.architecutresample.db.entity.ProductEntity;
import com.demo.tangminglong.architecutresample.model.Comment;
import com.demo.tangminglong.architecutresample.viewmodel.ProductViewModel;

import java.util.List;

/**
 * Created by tangminglong on 17/12/20.
 */

public class ProductFragment extends Fragment {

    private static final String KEY_PRODUCT_ID = "product_id";
    private ProductFragmentBinding mBinding;
    private CommentAdapter mCommentAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.product_fragment, container, false);
        mCommentAdapter = new CommentAdapter(new MyCommentCallBack());
        mBinding.commentList.setAdapter(mCommentAdapter);
        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductViewModel.Factory factory = new ProductViewModel.Factory(
                getActivity().getApplication(),getArguments().getInt(KEY_PRODUCT_ID));
        ProductViewModel viewModel = ViewModelProviders.of(this,factory)
                .get(ProductViewModel.class);
        mBinding.setProductViewModel(viewModel);
        subscribeToModel(viewModel);
    }

    private void subscribeToModel(ProductViewModel viewModel) {
       viewModel.getObservableProduct().observe(this, new Observer<ProductEntity>() {
           @Override
           public void onChanged(@Nullable ProductEntity productEntity) {
                viewModel.setProduct(productEntity);
           }
       });

       viewModel.getComments().observe(this, new Observer<List<CommentEntity>>() {
           @Override
           public void onChanged(@Nullable List<CommentEntity> commentEntities) {
                if (commentEntities != null){
                    mBinding.setIsLoading(false);
                    mCommentAdapter.setCommentList(commentEntities);
                }else {
                    mBinding.setIsLoading(true);

                }
           }
       });
    }

    public static ProductFragment newInstance(int productId){
        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PRODUCT_ID,productId);
        fragment.setArguments(bundle);
        return fragment;
    }


    private class MyCommentCallBack implements CommentCallBack {
        @Override
        public void onClick(Comment comment) {
            Toast.makeText(ProductFragment.this.getContext(), comment.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
