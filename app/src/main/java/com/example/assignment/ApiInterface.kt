package com.example.assignment

import retrofit2.http.GET

//api endpoint interface
interface ApiInterface {

    @GET("home")
    suspend fun getValues(): Response

    @GET("empty-home")
    suspend fun getEmpty(): Response

}