package com.example.mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.databinding.ActivityMainBinding
import com.example.mobile.databinding.FragmentStockBinding

class StockFragment : Fragment() {
    private val adapter=StockAdapter()
    private lateinit var binding:FragmentStockBinding
    private val im_name= listOf(R.string.app_name,R.string.app_name,R.string.app_name,R.string.app_name)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentStockBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =StockFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView =view.findViewById(R.id.rvStock)
        recyclerView.layoutManager =  GridLayoutManager(context,1)
        // adapter instance is set to the
        // recyclerview to inflate the items.
        recyclerView.adapter = adapter
        binding.buttonOn.setOnClickListener{
            val stock=Stock(R.drawable.baseline_analytics_24,"123","123","Сбербанк","СБЕР","asd","sad")
            adapter.addStock(stock)
        }
    }
}