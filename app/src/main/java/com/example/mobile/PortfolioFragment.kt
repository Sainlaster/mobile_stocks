package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        // adapter instance is set to the
        // recyclerview to inflate the items.
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
                                Stock(item.id,R.drawable.baseline_analytics_24, item.price, "123", item.name, item.ticket, "asd", "sad")
                            adapter.addStock(stock)
                        }
                    }
                    else
                    {
                        Log.i("MyLog","ASDASD");
                    };
                }
                override fun onFailure(call: Call<ResponseStockDto>, t: Throwable) {
                    Log.i("MyLog", t.stackTraceToString())
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