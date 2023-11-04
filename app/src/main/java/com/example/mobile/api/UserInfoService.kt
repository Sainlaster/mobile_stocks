package com.example.mobile.api

import com.example.mobile.model.ResponsUserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface UserInfoService {
    @GET("internal/marketplace/user-info")
    fun getUserInfo( @Header("Authorization") authorization: String): Call<ResponsUserInfo>
}