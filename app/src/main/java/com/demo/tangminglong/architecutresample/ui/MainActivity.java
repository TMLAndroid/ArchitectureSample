package com.demo.tangminglong.architecutresample.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.tangminglong.architecutresample.R;
import com.demo.tangminglong.architecutresample.model.Product;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            ProductListFragment fragment = new ProductListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container,fragment,ProductListFragment.TAG)
                    .commit();

        }
    }

    public void show(Product product){
        ProductFragment productFragment = ProductFragment.newInstance(product.getId());
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("prodcut")
                .replace(R.id.fragment_container,productFragment,null)
                .commit();

    }
}
