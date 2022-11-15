package com.ximcomputerx.formusic.network;

import android.util.Log;

import com.google.gson.Gson;
import com.ximcomputerx.formusic.config.Constant;
import com.ximcomputerx.formusic.model.RemoteReturnData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR HACKER
 */
public class RetrofitHelper {

    public final static int CONNECT_TIMEOUT = 600;
    public final static int READ_TIMEOUT = 600;
    public final static int WRITE_TIMEOUT = 600;

    private static Retrofit retrofit;
    private static ApiService apiService;

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            // 创建日志拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("retrofit", "log = " + message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            // 创建头部参数拦截器
            Interceptor headerInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder()
                            .addHeader("Accept-Encoding", "gzip")
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json; charset=utf-8")
                            .header("Cookie", "MUSIC_A=bf8bfeabb1aa84f9c8c3906c04a04fb864322804c83f5d607e91a04eae463c9436bd1a17ec353cf7b9ad24053d3ebd172d3c8caa69843f6a993166e004087dd3de8308e20a27a31186cc75b03239d9ea28a583cf45622fcb08b0baa99986c484807e650dd04abd3fb8130b7ae43fcc5b; __csrf=1d2b8f525128dc972a975db6de034f0f")
                            .method(originalRequest.method(), originalRequest.body());
                    // requestBuilder.addHeader("Authorization", "");
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            };
            // 客户端
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)       // 设置读超时时长
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)     // 设置写超时时长
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) // 设置连接超时时长
                    .retryOnConnectionFailure(false)                   // 错误重连
                    .addInterceptor(loggingInterceptor)                // 添加日志拦截器
                    .addInterceptor(headerInterceptor)                 // 添加参数拦截器
                    .build();
            // 客户端
            retrofit = new Retrofit.Builder()
                    .client(client)                 // 设置请求客户端
                    .addConverterFactory(GsonConverterFactory.create(new Gson())) // 添加转化库
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())     // 添加回调库
                    .baseUrl(Constant.URL_SERVICE)  // 设置服务器路径
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            apiService = getRetrofit().create(ApiService.class);
        }
        return apiService;
    }

    protected <T> Observable.Transformer<RemoteReturnData<T>, T> applySchedulers() {
        return (Observable.Transformer<RemoteReturnData<T>, T>) transformer;
    }

    final Observable.Transformer transformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1() {
                        @Override
                        public Object call(Object response) {
                            return flatResponse((RemoteReturnData<Object>) response);
                        }
                    });
        }
    };

    public <T> Observable<T> flatResponse(final RemoteReturnData<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (response.getCode() == 200) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(response.getData());
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(new Throwable(response.getMessage()));
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

}