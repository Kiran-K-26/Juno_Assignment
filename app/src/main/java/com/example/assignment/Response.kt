package com.example.assignment


import com.google.gson.annotations.SerializedName

//Data model class for response
data class Response(
    @SerializedName("all_transactions")
    val allTransactions: List<AllTransaction?>?,
    @SerializedName("crypto_balance")
    val cryptoBalance: CryptoBalance?,
    @SerializedName("crypto_prices")
    val cryptoPrices: List<CryptoPrice?>?,
    @SerializedName("your_crypto_holdings")
    val yourCryptoHoldings: List<YourCryptoHolding?>?
)

data class AllTransaction(
    @SerializedName("title")
    val title: String?,
    @SerializedName("txn_amount")
    val txnAmount: String?,
    @SerializedName("txn_logo")
    val txnLogo: String?,
    @SerializedName("txn_sub_amount")
    val txnSubAmount: String?,
    @SerializedName("txn_time")
    val txnTime: String?
)

data class YourCryptoHolding(
    @SerializedName("current_bal_in_token")
    val currentBalInToken: String?,
    @SerializedName("current_bal_in_usd")
    val currentBalInUsd: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("title")
    val title: String?
)

data class CryptoPrice(
    @SerializedName("current_price_in_usd")
    val currentPriceInUsd: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("title")
    val title: String?
)

data class CryptoBalance(
    @SerializedName("current_bal_in_usd")
    val currentBalInUsd: String?,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("title")
    val title: String?
)