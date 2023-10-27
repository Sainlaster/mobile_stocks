package com.example.mobile.api

import com.example.mobile.model.ResponseStockDto
import com.example.mobile.model.StockDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface StockIdService {
    @GET("internal/marketplace/stock")
    fun getStockById(@Query("id") id: String, @Header("Authorization") authorization: String): Call<StockDto>
}