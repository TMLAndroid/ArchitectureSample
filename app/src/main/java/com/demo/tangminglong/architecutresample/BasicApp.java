package com.demo.tangminglong.architecutresample;

import android.app.Application;

import com.demo.tangminglong.architecutresample.db.AppDatabase;

/**
 * Created by tangminglong on 17/12/20.
 */

public class BasicApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getDataRepository(){
        return DataRepository.getInstance(getDatabase());
    }
}
