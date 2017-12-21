package com.demo.tangminglong.architecutresample.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by tangminglong on 17/12/20.
 * 日期转换
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
