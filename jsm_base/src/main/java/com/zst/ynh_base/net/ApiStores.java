package com.zst.ynh_base.net;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiStores {

    String API_SERVER_URL = "http://jsm-test.nucdx.com/";

    @POST()
    Observable<ResponseBody> postJson(
            @Url() String url,
            @Body RequestBody jsonBody);


    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> executePost(
            @Url() String url,
            @FieldMap Map<String, String> maps);
    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> executeObjectPost(
            @Url() String url,
            @FieldMap Map<String, Object> maps);


    @GET()
    Observable<ResponseBody> executeGet(
            @Url String url,
            @QueryMap Map<String, String> maps);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    @Multipart
    @POST()
    Observable<ResponseBody> upload(@Url String fileUrl,@Part MultipartBody.Part imgs);

}
