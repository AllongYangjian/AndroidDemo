package com.allong.rxjavademo;

import com.allong.rxjavademo.customer.Observable;
import com.allong.rxjavademo.customer.Observer;

public class CustomSubscribeMode {

    //创建被观察者

    Observable<String> observable = Observable.create();

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            System.err.println(s);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public void dispose() {

        }
    };

    public void test(){
        observable.subscribe(observer);
        observable.doSomething("sss");
        observable.doSomething("sss");
        observable.doSomething("sss");
        observable.doSomething("sss");
    }

}
