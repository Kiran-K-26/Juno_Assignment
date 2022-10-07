package com.example.assignment

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.databinding.LayoutRecentTransactionsBinding
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

//Recent transactions adapter class
class AdapterRecentTransactions(
    private var allTransactions: List<AllTransaction?>,
    private val context: StateActivity
) : RecyclerView.Adapter<AdapterRecentTransactions.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun recentTransactions(
            allTransactions: List<AllTransaction?>,
            context: StateActivity,
            position: Int
        ) {
            with(LayoutRecentTransactionsBinding.bind(itemView)){
                tvRecentTitle.text = allTransactions[position]?.title
                tvRecentSubTitle.text = allTransactions[position]?.txnTime
                tvPriceTitle.text = "$${allTransactions[position]?.txnAmount}"
                tvPriceSubTitle.text = allTransactions[position]?.txnSubAmount

                if (allTransactions[position]?.txnLogo?.contains("svg")!!){
                    GlideToVectorYou.justLoadImage(context, Uri.parse(allTransactions[position]?.txnLogo),imgEthbtc)
                }else{
                    Glide.with(context).load(allTransactions[position]?.txnLogo).into(imgEthbtc)
                }

            }
        }
    }

    //setting content view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_recent_transactions, parent, false)
        )

    //setting adapter datas
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recentTransactions(allTransactions,context,position)
    }

    //list item count
    override fun getItemCount(): Int = allTransactions.size

    //refresh function for list data changes
    fun refreshTransactions(allTransactions: List<AllTransaction?>){
        this.allTransactions = allTransactions
        notifyDataSetChanged()
    }
}