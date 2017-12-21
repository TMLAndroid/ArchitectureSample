package com.demo.tangminglong.architecutresample.ui;

import android.view.View;

/**
 * Created by tangminglong on 17/12/20.
 */

public class BindingAdapter {
    @android.databinding.BindingAdapter("visibleGone")
    public static void showHide(View view,boolean show){
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
