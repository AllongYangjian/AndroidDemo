package com.allong.rxjavademo.customer;

/**
 * 中介
 */
public interface ObservableOnSubscribe<T> {

    void onNext(T t);

    void onError(Throwable e);

    void onComplete();
}
