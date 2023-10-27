package com.example.mobile.api
import com.example.mobile.model.ResponseStockDto
import com.example.mobile.model.StockDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url
interface StockService {
    @GET("internal/marketplace")
    fun getStock( @Header("Authorization") authorization: String): Call<ResponseStockDto>
}