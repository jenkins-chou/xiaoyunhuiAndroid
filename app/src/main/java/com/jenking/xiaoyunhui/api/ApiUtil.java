package com.jenking.xiaoyunhui.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhouzhenjian on 2018/3/26.
 * 通用ApiServce调入接口
 */

public class ApiUtil {
    public ApiUtil providerApiUtil;
    public Context context;
    private Retrofit retrofit = null;
    public ApiUtil setContext(Context context){
        if (providerApiUtil==null){
            providerApiUtil = new ApiUtil(context);
        }
        return providerApiUtil;
    }
    public ApiUtil(Context context){
        this.context = context;
        initRetrofit();
    }

    public ApiUtil(Context context, String base_url){
        this.context = context;
        initRetrofit(base_url);
    }
    public void initRetrofit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseAPI.base_url)//注意此处,设置服务器的地址
                .addConverterFactory(GsonConverterFactory.create(gson))//用于Json数据的转换,非必须
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//用于返回Rxjava调用,非必须
                .client(defaultOkHttpClient())
                .build();
}

    public void initRetrofit(String base_url){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)//注意此处,设置服务器的地址
                .addConverterFactory(GsonConverterFactory.create(gson))//用于Json数据的转换,非必须
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//用于返回Rxjava调用,非必须
                .client(defaultOkHttpClient())
                .build();
    }

    public <T> T getServer(final Class<T> t){
        System.out.println((T)retrofit.create(t).getClass());
        return (T)retrofit.create(t);
    }
    //设置连接与读取超时
    public OkHttpClient defaultOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.v("zzj--http:","-----------------> " + message);
                if (message!=null&&message.indexOf("Content-Type")!=-1){
                }else if (message!=null&&message.indexOf("Server")!=-1){
                }else if (message!=null&&message.indexOf("Set-Cookie")!=-1){
                }else if (message!=null&&message.indexOf("X-Powered-By")!=-1){
                }else if (message!=null&&message.indexOf("Date")!=-1){
                }else if (message!=null&&message.indexOf("Content-Length")!=-1){
                }else if (message!=null&&message.equals("")){
                }else if (message!=null&&message.indexOf("--> END POST")!=-1){
                }else if (message!=null&&message.indexOf("<-- END HTTP")!=-1){
                }else{
//                    FileHelper.append(FileHelper.EVENT_LOG_FILE,message+"\n");
                }
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")//application/x-www-form-urlencoded
                            .addHeader("Accept-Encoding", "gzip, deflate")
                            .addHeader("Connection", "keep-alive")
                            .addHeader("Accept", "*/*")
//                            .addHeader("Cookie", CookiesUtil.getCookie(CookiesUtil.KEY_ZIXUN_COOKIE)+"")
                            .build();
                        return chain.proceed(request);
                    }
                })
                .build();
        return client;
    }
}
