package com.example.assignment

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.databinding.LayoutCryptoHoldingBinding
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

//current prices adapter class
class AdapterCryptoHolding(
    private val state: String,
    private val yourCryptoHoldings: List<YourCryptoHolding?>,
    private val context: StateActivity,
    private val buyClickListener: BuyClickListener
): RecyclerView.Adapter<AdapterCryptoHolding.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun cryptoHolding(
            state: String,
            position: Int,
            yourCryptoHoldings: List<YourCryptoHolding?>,
            context: StateActivity,
            buyClickListener: BuyClickListener
        ) {
            with(LayoutCryptoHoldingBinding.bind(itemView)){
                if (state == "value"){
                    tvCryptoSubTitle.visibility = View.VISIBLE
                    llTextCryptoPrices.visibility = View.VISIBLE
                    llButtonCrypto.visibility = View.GONE
                }else{
                    tvCryptoSubTitle.visibility = View.GONE
                    llTextCryptoPrices.visibility = View.GONE
                    llButtonCrypto.visibility = View.VISIBLE
                }

                tvCryptoTitle.text = yourCryptoHoldings[position]?.title
                tvCryptoSubTitle.text = "${yourCryptoHoldings[position]?.currentBalInToken} ${yourCryptoHoldings[position]?.title}"
                tvPriceTitle.text = "$${yourCryptoHoldings[position]?.currentBalInUsd}"

                if (yourCryptoHoldings[position]?.logo?.contains("svg")!!){
                    //svg image handling
                    GlideToVectorYou.justLoadImage(context, Uri.parse(yourCryptoHoldings[position]?.logo),imgEthbtc)
                }else{
                    Glide.with(context).load(yourCryptoHoldings[position]?.logo).into(imgEthbtc)
                }

                llBtnBuy.setOnClickListener {
                    buyClickListener.buyClicks(yourCryptoHoldings[position])
                }
            }
        }
    }

    //setting content view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_crypto_holding,parent,false)
    )

    //setting adapter datas
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cryptoHolding(state,position,yourCryptoHoldings,context,buyClickListener)
    }

    //list item count
    override fun getItemCount(): Int = yourCryptoHoldings.size

    //interface for buy button click
    interface BuyClickListener{
        fun buyClicks(yourCryptoHolding: YourCryptoHolding?)
    }
}