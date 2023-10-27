package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.databinding.ActivityStockBinding
import com.example.mobile.model.StockDto
import com.example.mobile.repository.StockServiceRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StockActivity : AppCompatActivity() {
    private val stockServiceRepository: StockServiceRepository = StockServiceRepository()
    lateinit var bindingSt:ActivityStockBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val arguments = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        bindingSt=ActivityStockBinding.inflate(layoutInflater)

        val id= arguments?.getString("id")
        Log.i("LOGer",id.toString())
        val call = stockServiceRepository.getStockById(id.toString(),"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTg0ODkxNDksInVzZXJfaWQiOiI5MzY5YmYxZC0wMTEyLTRjMDktOWI5NC0xM2M4ODM1YzU0ZmYifQ.v6RmuLxHrBQvOALfBJJrmCKqgwPY5lS5SdA3swSmOqo")
        call.enqueue(
            object : Callback<StockDto> {
                override fun onResponse(
                    call: Call<StockDto>,
                    response: Response<StockDto>
                ) {

                    val responseAnswer: StockDto?=response.body()
                    if (responseAnswer != null) {
                           Log.i("MyLog",responseAnswer.toString())
                    }
                    else
                    {
                        Log.i("MyLog",call.request().toString())
                        Log.i("MyLog",response.message().toString())
                        Log.i("MyLog",response.code().toString())
                    };
                }
                override fun onFailure(call: Call<StockDto>, t: Throwable) {
                    Log.i("LOGer", t.stackTraceToString())
                }
            }
        )

    }
}