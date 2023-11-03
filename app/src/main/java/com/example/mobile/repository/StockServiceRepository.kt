package com.example.mobile.repository

import com.example.mobile.api.BuyStockService
import com.example.mobile.api.RetrofitInterface
import com.example.mobile.api.SignInService
import com.example.mobile.model.ResponseBuy
import com.example.mobile.model.ResponseStockDto
import com.example.mobile.model.SignInToken
import com.example.mobile.model.StockDto
import retrofit2.Call

class StockServiceRepository {
    fun getAllStock(token:String): Call<ResponseStockDto>{
        return RetrofitInterface.stockApi.getStock(token)
    }
    fun getStockById(id: String, token:String): Call<StockDto>{
        return RetrofitInterface.stockByIdApi.getStockById(id,token)
    }

    fun postSignIn(login:String, password:String):Call <SignInToken>{
        return RetrofitInterface.signInApi.getStockById(SignInService.SignRequestBody(login,password))
    }
    fun getMyStock(token:String): Call<ResponseStockDto>{
        return RetrofitInterface.stockMyApi.getMyStock(token)
    }
    fun postBuyStock(token: String, amount: Int, ticket:String):Call <ResponseBuy>{
        return RetrofitInterface.stockBuy.buyStock(token,BuyStockService.BuyRequestBody(amount,ticket))
    }
}