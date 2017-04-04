package com.qiangxi.developmentsample.net;

/**
 * Created by qiang_xi on 2017/4/4 20:38.
 */

public interface RetrofitCall<T> {
    void cancel();
    void enqueue(RetrofitCallback<T> callback);
    RetrofitCall<T> clone();
}
