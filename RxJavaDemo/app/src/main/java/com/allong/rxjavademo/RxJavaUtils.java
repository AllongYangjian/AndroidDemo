package com.allong.rxjavademo;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJavaUtils {

    //创建一个被观察者
    private Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            emitter.onNext("sfdadfa");
            emitter.onNext("sfdadfa");
            emitter.onNext("sfdadfa");
            emitter.onComplete();
        }
    });

    Observable<String> observable2 = Observable.just("");
    //创建一个观察者
    private Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            System.err.println(s);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            System.err.println("onComplete");
        }
    };

    //订阅
    public void subscribe(){
        observable.subscribe(observer);
//        observable2.subscribe();
    }

    public void subscribe2(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void subscribe3(){

    }


}
