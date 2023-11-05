package com.example.mobile.api

import com.example.mobile.model.ResponseBuy
import com.example.mobile.model.ResponsePolynom
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface PolynomialService {
    @GET("internal/polynomial")
    fun getPolynomial(@Header("Authorization") authorization: String): Call<ResponsePolynom>
}