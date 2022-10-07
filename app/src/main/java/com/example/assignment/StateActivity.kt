package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.databinding.ActivityStateBinding

class StateActivity : AppCompatActivity(),AdapterCryptoHolding.BuyClickListener {
    private lateinit var stateViewBinding: ActivityStateBinding
    private lateinit var stateViewModel: StateViewModel
    lateinit var adapterCurrentPrices: AdapterCurrentPrices
    lateinit var adapterRecentTransactions: AdapterRecentTransactions
    lateinit var adapterCryptoHolding: AdapterCryptoHolding
    private val allTransactions: ArrayList<AllTransaction?> = ArrayList()
    var state: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initialize View binding
        stateViewBinding = ActivityStateBinding.inflate(layoutInflater)
        setContentView(stateViewBinding.root)
        //Assigning states
        state = this.intent.extras?.getString("state")
        //Initialize ViewModel
        stateViewModel = ViewModelProvider(this)[StateViewModel::class.java]
//        Log.e("State", "onCreate: ${this.intent.extras?.getString("state")}")
        setStateView()
        callApiServices()
        observes()
    }

    private fun observes() {
        //observe value state
        stateViewModel.valueResponse.observe(this, Observer {
            stateViewBinding.nsv.visibility = View.VISIBLE
            stateViewBinding.progressBar.visibility = View.GONE
            if (it != null){
                if (it.cryptoBalance != null){
                    setCryptoAccount(it.cryptoBalance)
                }
                if (it.yourCryptoHoldings?.isNotEmpty()!!){
                    stateViewBinding.rvCryptoHolding.visibility = View.VISIBLE
                    stateViewBinding.tvHoldingEmpty.visibility = View.GONE
                    setCryptoHolding(it.yourCryptoHoldings)
                }else{
                    stateViewBinding.rvCryptoHolding.visibility = View.GONE
                    stateViewBinding.tvHoldingEmpty.visibility = View.VISIBLE
                }
                if (it.allTransactions?.isNotEmpty()!!){
                    stateViewBinding.rvRecentTransactions.visibility = View.VISIBLE
                    stateViewBinding.tvTransactionEmpty.visibility = View.GONE
                    setTransactions(it.allTransactions)
                }else{
                    stateViewBinding.rvRecentTransactions.visibility = View.GONE
                    stateViewBinding.tvTransactionEmpty.visibility = View.VISIBLE
                }
                if (it.cryptoPrices?.isNotEmpty()!!){
                    stateViewBinding.rvCurrentPrices.visibility = View.VISIBLE
                    stateViewBinding.tvPriceEmpty.visibility = View.GONE
                    setCurrentPrices(it.cryptoPrices)
                }else{
                    stateViewBinding.rvCurrentPrices.visibility = View.GONE
                    stateViewBinding.tvPriceEmpty.visibility = View.VISIBLE
                }
            }
        })

        //observe empty state
        stateViewModel.emptyResponse.observe(this, Observer {
            stateViewBinding.nsv.visibility = View.VISIBLE
            stateViewBinding.progressBar.visibility = View.GONE
            if (it != null){
                if (it.cryptoBalance != null){
                    setCryptoAccount(it.cryptoBalance)
                }
                if (it.yourCryptoHoldings?.isNotEmpty()!!){
                    stateViewBinding.rvCryptoHolding.visibility = View.VISIBLE
                    stateViewBinding.tvHoldingEmpty.visibility = View.GONE
                    setCryptoHolding(it.yourCryptoHoldings)
                }else{
                    stateViewBinding.rvCryptoHolding.visibility = View.GONE
                    stateViewBinding.tvHoldingEmpty.visibility = View.VISIBLE
                }
               if (it.cryptoPrices?.isNotEmpty()!!){
                   stateViewBinding.rvCurrentPrices.visibility = View.VISIBLE
                   stateViewBinding.tvPriceEmpty.visibility = View.GONE
                   setCurrentPrices(it.cryptoPrices)
               }else{
                   stateViewBinding.rvCurrentPrices.visibility = View.GONE
                   stateViewBinding.tvPriceEmpty.visibility = View.VISIBLE
               }
                it.allTransactions?.let { it1 -> setTransactions(it1) }
            }
        })
    }

    //set current price adapter
    private fun setCurrentPrices(cryptoPrices: List<CryptoPrice?>) {
        adapterCurrentPrices = AdapterCurrentPrices(cryptoPrices,this)
        stateViewBinding.rvCurrentPrices.adapter = adapterCurrentPrices
    }

    //set recent transactions adapter
    private fun setTransactions(allTransactions: List<AllTransaction?>) {
        adapterRecentTransactions = AdapterRecentTransactions(allTransactions,this)
        stateViewBinding.rvRecentTransactions.adapter = adapterRecentTransactions
    }

    //set crypto holding adapter
    private fun setCryptoHolding(yourCryptoHoldings: List<YourCryptoHolding?>) {
        adapterCryptoHolding = AdapterCryptoHolding(state!!,yourCryptoHoldings,this,this)
        stateViewBinding.rvCryptoHolding.adapter = adapterCryptoHolding
    }

    //set crypto account details
    private fun setCryptoAccount(cryptoBalance: CryptoBalance) {
        stateViewBinding.tvCryptoAccount.text = cryptoBalance.title
        stateViewBinding.tvCryptos.text = cryptoBalance.subtitle
        stateViewBinding.tvValueAccountTitle.text = "$${cryptoBalance.currentBalInUsd}"
    }

    //calling api services
    private fun callApiServices() {
        stateViewBinding.nsv.visibility = View.GONE
        stateViewBinding.progressBar.visibility = View.VISIBLE
        if (state == "value"){
            stateViewModel.getValueState()
        }else{
            stateViewModel.getEmptyState()
        }
    }

    //change ui according to state
    private fun setStateView() {
        if (state == "value"){
            stateViewBinding.llEmptyCrypto.visibility = View.GONE
            stateViewBinding.tvValueAccountTitle.visibility = View.VISIBLE
            stateViewBinding.tvValueAccountSubTitle.visibility = View.VISIBLE
        }else{
            stateViewBinding.llEmptyCrypto.visibility = View.VISIBLE
            stateViewBinding.tvValueAccountTitle.visibility = View.GONE
            stateViewBinding.tvValueAccountSubTitle.visibility = View.GONE
        }
    }

    //interface call for buy button click
    override fun buyClicks(yourCryptoHolding: YourCryptoHolding?) {
        val allTransaction = AllTransaction(yourCryptoHolding?.title,yourCryptoHolding?.currentBalInUsd,yourCryptoHolding?.logo,yourCryptoHolding?.currentBalInToken,"Just now")
        allTransactions.add(allTransaction)
        adapterRecentTransactions.refreshTransactions(allTransactions)
    }
}