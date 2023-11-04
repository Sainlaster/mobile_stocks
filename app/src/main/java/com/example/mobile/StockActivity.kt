package com.example.mobile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.databinding.ActivityStockBinding
import com.example.mobile.model.ResponseBuy
import com.example.mobile.model.StockDto
import com.example.mobile.repository.StockServiceRepository
import com.example.mobile.uservariables.UserVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class StockActivity : AppCompatActivity() {
    private val stockServiceRepository: StockServiceRepository = StockServiceRepository()
    lateinit var bindingSt:ActivityStockBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val arguments = intent.extras
        super.onCreate(savedInstanceState)
        bindingSt=ActivityStockBinding.inflate(layoutInflater)
        setContentView(bindingSt.root)

        val id= arguments?.getString("id")
        Log.i("MyLog",id.toString())
        val call = stockServiceRepository.getStockById(id.toString(),UserVariables.token)
        call.enqueue(
            object : Callback<StockDto> {
                override fun onResponse(
                    call: Call<StockDto>,
                    response: Response<StockDto>
                ) {
                    val responseAnswer: StockDto?=response.body()
                    if (responseAnswer != null) {
                        Log.i("MyLog",responseAnswer.toString())
                        bindingSt.activitystockname.text=responseAnswer.name
                        bindingSt.activityStockTicket.text=responseAnswer.ticket
                        bindingSt.activityStockPrice.text=responseAnswer.price
                        bindingSt.ishort.text=responseAnswer.is_short
                        bindingSt.step.text=responseAnswer.price_step
                        bindingSt.lotsize.text=responseAnswer.lot_size
                        bindingSt.activityStockButtonBuy.setOnClickListener{
                            val call = stockServiceRepository.postBuyStock(UserVariables.token,1,responseAnswer.ticket)
                            call.enqueue(
                                object : Callback<ResponseBuy> {
                                    override fun onResponse(
                                        call: Call<ResponseBuy>,
                                        response: Response<ResponseBuy>
                                    ) {
                                        val responseAnswer: ResponseBuy?=response.body()
                                        if (responseAnswer != null) {
                                            Log.i("MyLog",responseAnswer.toString())
                                            val message = "Успешная покупка!"
                                            val duration = Toast.LENGTH_LONG // или Toast.LENGTH_LONG
                                            val toast = Toast.makeText(applicationContext, message, duration)
                                            toast.show()
                                        }
                                        else
                                        {
                                            Log.i("MyLog",call.request().toString())
                                            Log.i("MyLog",response.message().toString())
                                            Log.i("MyLog",response.code().toString())
                                            if(response.code()!=200){
                                                val message = "Не удалось совершить покупку"
                                                val duration = Toast.LENGTH_LONG // или Toast.LENGTH_LONG
                                                val toast = Toast.makeText(applicationContext, message, duration)
                                                toast.show()
                                            }
                                        };
                                    }
                                    override fun onFailure(call: Call<ResponseBuy>, t: Throwable) {
                                        Log.i("MyLog", t.stackTraceToString())
                                        val message = "Не удалось совершить покупку, пожалуйста, попробуйте позже"
                                        val duration = Toast.LENGTH_LONG // или Toast.LENGTH_LONG
                                        val toast = Toast.makeText(applicationContext, message, duration)
                                        toast.show()
                                    }
                                }
                            )
                        }
                    }
                    else
                    {
                        Log.i("MyLog",call.request().toString())
                        Log.i("MyLog",response.message().toString())
                        Log.i("MyLog",response.code().toString())
                    };
                }
                override fun onFailure(call: Call<StockDto>, t: Throwable) {
                    Log.i("MyLog", t.stackTraceToString())
                }
            }
        )
    }

}