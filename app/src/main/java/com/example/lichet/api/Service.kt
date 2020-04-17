package com.example.lichet.api

import com.example.lichet.api.request.HeartBeatRequest
import com.example.lichet.api.response.HeartBeatResponse
import io.reactivex.Single
import retrofit2.http.*

interface Service {
    /**
     * テスト
     * requestIndexで指定したレコード以降のデータをリストで受け取る
     */
    @POST("v1/api/get_data/pnn/")
    fun getHearBeats(@Body heartBeatRequest: HeartBeatRequest): Single<HeartBeatResponse>
//
//    /**
//     * PORTへのログイン(company_user)
//     */
//    @POST("auth/sign_in.json")
//    fun login(@Body login: LoginCompanyUserRequest): Single<CompanyUser>
//

}