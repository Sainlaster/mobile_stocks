package com.example.mobile
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.example.mobile.databinding.StockItemBinding
import coil.decode.SvgDecoder
import coil.load
class StockAdapter( val listener: Listener):RecyclerView.Adapter<StockAdapter.StockHolder>() {
    private val  stockList=ArrayList<Stock>()
    class StockHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding = StockItemBinding.bind(item)
            fun bind(stock: Stock,listener: Listener) {
                binding.name.text = stock.name
                binding.change.text = stock.change
                binding.price.text = stock.price
                binding.platform.text = stock.platform
                binding.changePercent.text = stock.changePercent
                binding.ticket.text = stock.ticket
                binding.stockCard.setOnClickListener{
                    listener.onClick(stock)
                }
                val onlineSvgImage = binding.image
                val svgUrl = stock.imageURL
                onlineSvgImage.load(svgUrl) {
                    decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
                }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockHolder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.stock_item,parent,false)
        return StockHolder(view)
    }
    override fun onBindViewHolder(holder: StockHolder, position: Int) {
        holder.bind(stockList[position], listener)
    }
    override fun getItemCount(): Int {
        return stockList.size
    }

    fun addStock(stock :Stock){
        stockList.add(stock)
        notifyDataSetChanged()

    }
    interface Listener{
        fun onClick(stock:Stock)
    }
}