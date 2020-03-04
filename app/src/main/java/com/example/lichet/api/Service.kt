package com.example.lichet.api

import com.example.lichet.api.response.HeartBeat
import io.reactivex.Single
import retrofit2.http.*

interface Service {
    /**
     * テスト
     * requestIndexで指定したレコード以降のデータをリストで受け取る
     */
    @GET("api/get_pnn/{userId}/{measurementId}/{requestIndex}")
    fun getHearBeats(@Path("userId") userId: Int,
                     @Path("measurementId") measurementId: Int,
                     @Path("requestIndex") requestIndex: Int): Single<List<HeartBeat>>
//
//    /**
//     * PORTへのログイン(company_user)
//     */
//    @POST("auth/sign_in.json")
//    fun login(@Body login: LoginCompanyUserRequest): Single<CompanyUser>
//

}