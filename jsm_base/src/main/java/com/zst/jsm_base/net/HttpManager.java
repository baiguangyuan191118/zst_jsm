package com.zst.jsm_base.net;

import android.content.Context;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zst.jsm_base.BaseApplication;
import com.zst.jsm_base.BuildConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HttpManager {

    private static final long DEFAULT_TIMEOUT = 30;
    private static Retrofit mRetrofit;
    private static HashMap<String, String> header;
    private CompositeSubscription mCompositeSubscription;
    private static OnGlobalInterceptor onGlobalInterceptor;
    private Observable<ResponseBody> downObservable;
    private Map<Object, Observable<ResponseBody>> downMaps = new HashMap<Object, Observable<ResponseBody>>() {
    };

    private Context context;


    public void setContext(Context context) {
        this.context = context;
    }

    public HttpManager() {
        mCompositeSubscription = new CompositeSubscription();
    }

    public HttpManager(Context context) {
        mCompositeSubscription = new CompositeSubscription();
        this.context = context;
    }

    public static void setOnGlobalInterceptor(OnGlobalInterceptor interceptor) {
        onGlobalInterceptor = interceptor;
    }


    /**
     * 设置请求头
     *
     * @param questHeader
     * @return
     */
    public static synchronized void setHeader(HashMap<String, String> questHeader) {
        header = questHeader;
        createRetrofitInstance();
    }

    private static synchronized Retrofit retrofit() {
        if (mRetrofit == null) {
            createRetrofitInstance();
        }
        return mRetrofit;
    }

    private static void createRetrofitInstance() {
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BaseApplication.getContext()));
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


        OkHttpClient okHttpClient = builder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiStores.API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * post方法返回string数据
     * @param url
     * @param param
     * @param subscriber
     */
    public void executePostString(String url, Map<String, String> param, ResponseCallBack<String> subscriber) {
        Subscription subscribe = retrofit().create(ApiStores.class)
                .executePost(url, param)
                .compose(schedulersTransformer)
                .subscribe(new StringCallback(context, subscriber, onGlobalInterceptor));
        mCompositeSubscription.add(subscribe);

    }

    /**
     * post方法返回json（传入的参数都未string）
     * @param url
     * @param param
     * @param callBack
     * @param <T>
     */
    public <T> void executePostJson(final String url, Map<String, String> param, final ResponseCallBack<T> callBack) {
        Subscription subscribe = retrofit().create(ApiStores.class)
                .executePost(url, param)
                .compose(schedulersTransformer)
                .subscribe(new JsonCallback<T>(callBack, onGlobalInterceptor, context));
        mCompositeSubscription.add(subscribe);
    }

    /**
     * post方法返回json（传入的参数value为object）
     * @param url
     * @param param
     * @param callBack
     * @param <T>
     */
    public <T> void executeObjectPost(final String url, Map<String, Object> param, final ResponseCallBack<T> callBack) {
        Subscription subscribe = retrofit().create(ApiStores.class)
                .executeObjectPost(url, param)
                .compose(schedulersTransformer)
                .subscribe(new JsonCallback<T>(callBack, onGlobalInterceptor, context));
        mCompositeSubscription.add(subscribe);
    }

    /**
     * get方法返回sting
     * @param url
     * @param param
     * @param subscriber
     */
    public void get(String url, Map<String, String> param, ResponseCallBack<String> subscriber) {
        Subscription subscribe = retrofit().create(ApiStores.class)
                .executeGet(url, param)
                .compose(schedulersTransformer)
                .subscribe(new StringCallback(context, subscriber, onGlobalInterceptor));
        mCompositeSubscription.add(subscribe);

    }

    /**
     * get方法返回json
     * @param url
     * @param param
     * @param callBack
     * @param <T>
     */
    public <T> void executeGet(final String url, Map<String, String> param, final ResponseCallBack<T> callBack) {

        Subscription subscribe = retrofit().create(ApiStores.class)
                .executeGet(url, param)
                .compose(schedulersTransformer)
                .subscribe(new JsonCallback<T>(callBack, onGlobalInterceptor, context));

        mCompositeSubscription.add(subscribe);
    }







    /**
     * RXJAVA schedulersTransformer
     * <p>
     * Schedulers.io()
     */
    final Observable.Transformer schedulersTransformerDown = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        }
    };


    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    private static Observable.Transformer schedulersTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };


    /**
     * ResponseCallBack <T> Support your custom data model
     */
    public  interface ResponseCallBack<T> {

        public void onCompleted();

        public abstract void onError(String errorMSG);

        public abstract void onSuccess(T response);

    }

    public static interface OnGlobalInterceptor {
        void onInterceptor();
    }


}
