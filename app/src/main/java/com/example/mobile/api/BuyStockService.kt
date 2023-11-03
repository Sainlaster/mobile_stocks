package com.example.mobile.api

import com.example.mobile.model.ResponseBuy
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BuyStockService {
    data class BuyRequestBody(val amount: Int, val ticket: String)
    @POST("internal/marketplace/buy")
    fun buyStock( @Header("Authorization") authorization: String,@Body body: BuyRequestBody): Call<ResponseBuy>
}