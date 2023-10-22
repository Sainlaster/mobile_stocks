package com.example.mobile

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.databinding.StockItemBinding

class StockAdapter:RecyclerView.Adapter<StockAdapter.StockHolder>() {
    val  stockList=ArrayList<Stock>()
    class StockHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding = StockItemBinding.bind(item)
            fun bind(stock: Stock) {
                binding.name.text = stock.name
                binding.image.setImageDrawable(stock.imageURL.toDrawable())
                binding.change.text = stock.change
                binding.price.text = stock.price
                binding.platform.text = stock.platform
                binding.changePercent.text = stock.changePercent
                binding.ticket.text = stock.ticket
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.stock_item,parent,false)
        return StockHolder(view)
    }
    override fun onBindViewHolder(holder: StockHolder, position: Int) {
        holder.bind(stockList[position])
    }
    override fun getItemCount(): Int {
        return stockList.size
    }

    fun addStock(stock :Stock){
        stockList.add(stock)
        notifyDataSetChanged()

    }
}