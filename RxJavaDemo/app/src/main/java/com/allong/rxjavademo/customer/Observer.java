package com.allong.rxjavademo.customer;

/**
 * 观察者
 */
public interface Observer<T> {

    void onNext(T t);

    void onError(Throwable e);

    void onComplete();

    void dispose();
}
