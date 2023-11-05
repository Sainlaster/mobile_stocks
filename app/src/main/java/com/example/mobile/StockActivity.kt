package com.example.mobile

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.databinding.ActivityStockBinding
import com.example.mobile.model.ResponseBuy
import com.example.mobile.model.ResponsePolynom
import com.example.mobile.model.ResponseSell
import com.example.mobile.model.StockDto
import com.example.mobile.repository.StockServiceRepository
import com.example.mobile.uservariables.UserVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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
                        bindingSt.activityStockButtonSell.setOnClickListener{
                            val calls = stockServiceRepository.deleteSellStock(UserVariables.token,1,responseAnswer.ticket)
                            calls.enqueue(
                                object : Callback<ResponseSell> {
                                    override fun onResponse(
                                        call: Call<ResponseSell>,
                                        response: Response<ResponseSell>
                                    ) {
                                        val responseAnswer: ResponseSell?=response.body()
                                        if (responseAnswer != null) {
                                            Log.i("MyLog",responseAnswer.toString())
                                            val message = "Успешная продажа!"
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
                                                val message = "Не удалось совершить продажу"
                                                val duration = Toast.LENGTH_LONG // или Toast.LENGTH_LONG
                                                val toast = Toast.makeText(applicationContext, message, duration)
                                                toast.show()
                                            }
                                        };
                                    }
                                    override fun onFailure(call: Call<ResponseSell>, t: Throwable) {
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
        val callPolynomial=stockServiceRepository.getPolynomial(UserVariables.token)
        callPolynomial.enqueue(
            object : Callback<ResponsePolynom> {
                override fun onResponse(
                    call: Call<ResponsePolynom>,
                    response: Response<ResponsePolynom>
                ) {
                    val responseAnswer: ResponsePolynom?=response.body()
                    if (responseAnswer != null) {
                        Log.i("MyLog",responseAnswer.toString())
                        val lineChart = findViewById<LineChart>(R.id.lineChart)
                        val entries = ArrayList<Entry>()
                        var k=responseAnswer.polynomialCoefficients[24].toFloat()
                        k=bindingSt.activityStockPrice.text.toString().toFloat()/k
                        var list=Array(25) { 5.3f }
                        for (i in 0 until 25) {
                            val rnds = (1..100).random()
                            list[i]=(responseAnswer.polynomialCoefficients[i].toFloat()*(rnds/100.0f))
                        }
                        for (i in 0 until 25) {
                            entries.add(Entry(i.toFloat(),k*list[i]))
                        }
                        val dataSet = LineDataSet(entries, "График")

                        // Настройка свойств набора данных, например, цвет, стиль и другие
                        dataSet.setDrawCircles(false)
                        dataSet.color = Color.parseColor("#008C75")
                        dataSet.setCircleColor(Color.RED)
                        dataSet.valueTextSize = 12f
                        dataSet.setDrawValues(false)
                        lineChart.setBackgroundColor(Color.parseColor("#192238"))
                        // Создание объекта LineData и добавление в него набора данных
                        val lineData = LineData(dataSet)
                        lineChart.xAxis.textColor = Color.WHITE // Цвет текста по оси X
                        lineChart.axisRight.textColor = Color.WHITE // Цвет текста по оси Y
                        lineChart.legend.textColor = Color.WHITE // Цвет текста в легенде
                        // Настройка визуальных аспектов графика
                        lineChart.data = lineData
                        lineChart.description.isEnabled = false
                        lineChart.setTouchEnabled(true)
                        lineChart.isDragEnabled = true
                        lineChart.setScaleEnabled(true)
                        lineChart.setPinchZoom(true)
                        lineChart.setNoDataText("No data available")
                        lineChart.legend.form = Legend.LegendForm.LINE
                        lineChart.legend.isEnabled = false
                        val xAxis = lineChart.xAxis
                        xAxis.position = XAxis.XAxisPosition.BOTTOM
                        xAxis.setDrawGridLines(false)
                        xAxis.isEnabled=false

                        val leftAxis = lineChart.axisLeft
                        leftAxis.isEnabled =false

                        val rightAxis = lineChart.axisRight
                        rightAxis.isEnabled =true
                        rightAxis.setDrawLabels(true)
                        rightAxis.setDrawGridLines(false)
                        rightAxis.axisMinimum = 0f

                        // Обновление графика
                        lineChart.invalidate()
                    }
                    else
                    {
                        Log.i("MyLog",call.request().toString())
                        Log.i("MyLog",response.message().toString())
                        Log.i("MyLog",response.code().toString())
                    };
                }
                override fun onFailure(call: Call<ResponsePolynom>, t: Throwable) {
                    Log.i("MyLog", t.stackTraceToString())
                }
            }
        )
    }

}