package com.allong.rxjavademo.customer;

/**
 * 被观察者
 */
public class Observable<T> {

    private Observer<T> observer;


    public static <T> Observable<T> create() {
        return new Observable<T>();
    }

    public void subscribe(Observer<T> observer) {
        this.observer = observer;
    }

    public void doSomething(T t) {
        try {
            observableOnSubscribe.onNext(t);
        } catch (Exception e) {
            observableOnSubscribe.onError(e);
        }

    }

    private ObservableOnSubscribe<T> observableOnSubscribe = new ObservableOnSubscribe<T>() {
        @Override
        public void onNext(T t) {
            observer.onNext(t);
        }

        @Override
        public void onError(Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    };

}
