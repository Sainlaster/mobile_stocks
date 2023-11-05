package com.example.mobile.api

import com.example.mobile.model.ResponseBuy
import com.example.mobile.model.ResponseSell
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.HTTP
import retrofit2.http.Header

interface SellStockService {
    data class SellRequestBody(val amount: Int, val ticket: String)
    @HTTP(method = "DELETE", path = "internal/marketplace/sell", hasBody = true)
    fun sellStock(@Header("Authorization") authorization: String, @Body body: SellStockService.SellRequestBody): Call<ResponseSell>
}