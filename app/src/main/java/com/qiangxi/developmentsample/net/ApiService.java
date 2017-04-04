package com.qiangxi.developmentsample.net;

import com.qiangxi.developmentsample.entity.QueryResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by qiang_xi on 2017/4/4 16:42.
 * 所有API构建集
 */

interface ApiService {
    @FormUrlEncoded
    @POST(Api.VALIDATE_CODE)
    RetrofitCall<QueryResult<String>>  getSmsValidateCode(@Field("customerPhone") String customerPhone);
}
