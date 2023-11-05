package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.model.ResponsUserInfo
import com.example.mobile.model.ResponseStockDto
import com.example.mobile.repository.StockServiceRepository
import com.example.mobile.uservariables.UserVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PortfolioFragment : Fragment(), StockAdapter.Listener {
    private val adapter = StockAdapter(this)
    private val stockServiceRepository: StockServiceRepository = StockServiceRepository()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portfolio, container, false)
    }
    companion object {
        @JvmStatic
        fun newInstance() = PortfolioFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvStockMy)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.adapter = adapter
        Log.i("MyLog", UserVariables.token);
        val call = stockServiceRepository.getMyStock(UserVariables.token)
        call.enqueue(
            object : Callback<ResponseStockDto> {
                override fun onResponse(
                    call: Call<ResponseStockDto>,
                    response: Response<ResponseStockDto>
                ) {
                    val responseAnswer: ResponseStockDto?=response.body()
                    if (responseAnswer != null) {
                        for(item in responseAnswer.items){
                            val stock =
                                Stock(item.id,item.logo, item.price, "NASDAQ", item.name, item.ticket, "0", "0")
                            adapter.addStock(stock)
                        }
                    }
                    else
                    {
                        Log.i("MyLog","ASDASD");
                        val message = "Произошла ошибка, мы все исправляем, пожалуйста, подождите"
                        val duration = Toast.LENGTH_LONG // или Toast.LENGTH_LONG
                        val toast = Toast.makeText(requireContext(), message, duration)
                        toast.show()
                    };
                }
                override fun onFailure(call: Call<ResponseStockDto>, t: Throwable) {
                    Log.i("MyLog", t.stackTraceToString())
                    val message = "Произошла ошибка, мы все исправляем, пожалуйста, подождите"
                    val duration = Toast.LENGTH_LONG // или Toast.LENGTH_LONG
                    val toast = Toast.makeText(requireContext(), message, duration)
                    toast.show()
                }
            }
        )
        val callUser = stockServiceRepository.getUserInfo(UserVariables.token)
        callUser.enqueue(
            object : Callback<ResponsUserInfo> {
                override fun onResponse(
                    call: Call<ResponsUserInfo>,
                    response: Response<ResponsUserInfo>
                ) {
                    val responseAnswer: ResponsUserInfo?=response.body()
                    if (responseAnswer != null) {
                        Log.i("MyLog",responseAnswer.toString());
                        val b= view.findViewById<TextView>(R.id.userBalance)
                        b.text=responseAnswer.user_info.balance.toString()+"$"
                    }
                    else
                    {
                        if(response.code()!=200) {
                            Log.i("MyLog", "ASDASD");
                            val message =
                                "Произошла ошибка, мы все исправляем, пожалуйста, подождите"
                            val duration = Toast.LENGTH_LONG
                            val toast = Toast.makeText(requireContext(), message, duration)
                            toast.show()
                        }
                    };
                }
                override fun onFailure(call: Call<ResponsUserInfo>, t: Throwable) {
                    Log.i("MyLog", t.stackTraceToString())
                    val message = "Произошла ошибка, мы все исправляем, пожалуйста, подождите"
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(requireContext(), message, duration)
                    toast.show()
                }
            }
        )
    }

    override fun onClick(stock: Stock) {
        var intent = Intent(activity, StockActivity::class.java)
        intent.putExtra("id",stock.id)
        startActivity(intent);
    }
}