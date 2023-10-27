package com.example.mobile.repository

import com.example.mobile.api.RetrofitInterface
import com.example.mobile.model.ResponseStockDto
import com.example.mobile.model.StockDto
import retrofit2.Call
import retrofit2.http.Header

class StockServiceRepository {
    fun getAllStock(token:String): Call<ResponseStockDto>{
        return RetrofitInterface.stockApi.getStock(token)
    }
    fun getStockById(id: String, token:String): Call<StockDto>{
        return RetrofitInterface.stockByIdApi.getStockById(id,token)
    }
}