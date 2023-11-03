package com.example.mobile.api

import com.example.mobile.model.ResponseStockDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface PortfolioStockService {
    @GET("internal/marketplace/my-stocks")
    fun getMyStock( @Header("Authorization") authorization: String): Call<ResponseStockDto>
}