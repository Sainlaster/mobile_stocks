package com.example.mobile.api

import com.example.mobile.model.SignInToken
import com.example.mobile.model.StockDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SignInService {
    data class SignRequestBody(val login: String, val password: String)
    @POST("internal/login")
    fun getStockById(@Body authorization: SignRequestBody): Call<SignInToken>
}