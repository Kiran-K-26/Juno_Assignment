package com.example.assignment

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.databinding.LayoutCurrentPriceBinding
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

//current prices adapter class
class AdapterCurrentPrices(private val cryptoPrices: List<CryptoPrice?>, private val context: StateActivity) : RecyclerView.Adapter<AdapterCurrentPrices.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun currentPrices(cryptoPrices: List<CryptoPrice?>, context: StateActivity, position: Int) {
            with(LayoutCurrentPriceBinding.bind(itemView)){
                tvPriceTitle.text = cryptoPrices[position]?.title
                tvPrice.text = "$${cryptoPrices[position]?.currentPriceInUsd}"

                if (cryptoPrices[position]?.logo?.contains("svg")!!){
                    //svg image handling
                    GlideToVectorYou.justLoadImage(context, Uri.parse(cryptoPrices[position]?.logo),imgPrice)
                }else{
                    Glide.with(context).load(cryptoPrices[position]?.logo).into(imgPrice)
                }
            }
        }
    }

    //setting content view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_current_price, parent, false)
        )

    //setting adapter datas
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.currentPrices(cryptoPrices,context,position)
    }

    //list item count
    override fun getItemCount(): Int = cryptoPrices.size
}