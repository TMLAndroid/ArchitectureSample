package com.demo.tangminglong.architecutresample.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.demo.tangminglong.architecutresample.R;
import com.demo.tangminglong.architecutresample.databinding.CommentItemBinding;
import com.demo.tangminglong.architecutresample.model.Comment;

import java.util.List;
import java.util.Objects;

/**
 * Created by tangminglong on 17/12/21.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private CommentCallBack mCallBack;
    private List<? extends Comment> mCommentList;
    public CommentAdapter(@Nullable CommentCallBack callBack) {
        this.mCallBack = callBack;
    }


    public void setCommentList(List<? extends Comment> commentList){
        if (mCommentList == null){
            this.mCommentList = commentList;
            notifyItemRangeInserted(0,commentList.size());
        }else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mCommentList.size();
                }

                @Override
                public int getNewListSize() {
                    return commentList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mCommentList.get(oldItemPosition).getId() ==
                            commentList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Comment oldComment = mCommentList.get(oldItemPosition);
                    Comment newComment = commentList.get(newItemPosition);
                    return oldComment.getId() == newComment.getId()
                            && oldComment.getPostedAt() == newComment.getPostedAt()
                            && oldComment.getProductId() == newComment.getProductId()
                            && Objects.equals(oldComment.getText(), newComment.getText());
                }
            });
            this.mCommentList = commentList;
            diffResult.dispatchUpdatesTo(this);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item, parent, false);
        binding.setCallback(mCallBack);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setComment(mCommentList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCommentList == null ? 0 : mCommentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CommentItemBinding binding;
        public ViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
