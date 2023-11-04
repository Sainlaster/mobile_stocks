package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.api.StockService
import com.example.mobile.databinding.ActivityMainBinding
import com.example.mobile.databinding.FragmentStockBinding
import com.example.mobile.model.ResponseStockDto
import com.example.mobile.repository.StockServiceRepository
import com.example.mobile.uservariables.UserVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StockFragment : Fragment(), StockAdapter.Listener {
    private val stockServiceRepository: StockServiceRepository = StockServiceRepository()
    private val adapter = StockAdapter(this)
    private lateinit var binding: FragmentStockBinding
    private val im_name =
        listOf(R.string.app_name, R.string.app_name, R.string.app_name, R.string.app_name)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStockBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = StockFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvStock)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        // adapter instance is set to the
        // recyclerview to inflate the items.
        recyclerView.adapter = adapter
        Log.i("MyLog",UserVariables.token);
        val call = stockServiceRepository.getAllStock(UserVariables.token)
        call.enqueue(
            object : Callback<ResponseStockDto> {
                override fun onResponse(
                    call: Call<ResponseStockDto>,
                    response: Response<ResponseStockDto>
                ) {
                    val responseAnswer:ResponseStockDto?=response.body()
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
    }

    override fun onClick(stock: Stock) {
        var intent = Intent(activity, StockActivity::class.java)
        intent.putExtra("id",stock.id)
        startActivity(intent);
    }
}