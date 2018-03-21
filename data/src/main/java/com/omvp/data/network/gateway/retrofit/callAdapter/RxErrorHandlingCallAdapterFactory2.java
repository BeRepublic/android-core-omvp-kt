package com.omvp.data.network.gateway.retrofit.callAdapter;

import com.omvp.data.network.gateway.retrofit.exception.RetrofitException;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RxErrorHandlingCallAdapterFactory2 extends CallAdapter.Factory {

    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory2() {
        original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory2();
    }

    @Override
    @SuppressWarnings("unchecked")
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {

        private final Retrofit retrofit;
        private final CallAdapter<R, Object> wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<R, Object> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object adapt(Call<R> call) {
            Object result = wrapped.adapt(call);
            if (result instanceof Single) {
                return ((Single) result).onErrorResumeNext(new Function<Throwable, SingleSource>() {
                    @Override
                    public SingleSource apply(@NonNull Throwable throwable) throws Exception {
                        return Single.error(asRetrofitException(throwable));
                    }
                });
            } else if (result instanceof Maybe) {
                return ((Maybe) result).onErrorResumeNext(new Function<Throwable, MaybeSource>() {
                    @Override
                    public MaybeSource apply(@NonNull Throwable throwable) throws Exception {
                        return Maybe.error(asRetrofitException(throwable));
                    }
                });
            } else if (result instanceof Completable) {
                return ((Completable) result).onErrorResumeNext(new Function<Throwable, CompletableSource>() {
                    @Override
                    public CompletableSource apply(@NonNull Throwable throwable) throws Exception {
                        return Completable.error(asRetrofitException(throwable));
                    }
                });
            } else if (result instanceof Flowable) {
                return ((Flowable) result).onErrorResumeNext(new Function<Throwable, Publisher>() {
                    @Override
                    public Publisher apply(Throwable throwable) throws Exception {
                        return Flowable.error(asRetrofitException(throwable));
                    }
                });
            } else if (result instanceof Observable) {
                return ((Observable) result).onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                    @Override
                    public ObservableSource apply(@NonNull Throwable throwable) throws Exception {
                        return Observable.error(asRetrofitException(throwable));
                    }
                });
            }
            return result;
        }

        private RetrofitException asRetrofitException(Throwable throwable) {
            // We had non-200 http error
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                return RetrofitException.Companion.httpError(response.raw().request().url().toString(), response, retrofit);
            }
            // A network error happened
            if (throwable instanceof IOException) {
                return RetrofitException.Companion.networkError((IOException) throwable);
            }
            // We don't know what happened. We need to simply convert to an unknown error
            return RetrofitException.Companion.unexpectedError(throwable);
        }

    }
}