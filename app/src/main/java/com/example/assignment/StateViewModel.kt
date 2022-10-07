package com.example.assignment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StateViewModel : ViewModel() {
    //instance for mutableLiveData and LiveData
    private val _valueResponse = MutableLiveData<Response>()
    val valueResponse: LiveData<Response>
        get() = _valueResponse

    private val _emptyResponse = MutableLiveData<Response>()
    val emptyResponse: LiveData<Response>
        get() = _emptyResponse


    fun getValueState(){
        //api call using coroutines
        viewModelScope.launch {
            try {
                val response = RetrofitClient.getRetrofit().getValues()
                _valueResponse.value = response
            }catch (e: Exception){
                _valueResponse.value = null
                Log.e("ValueException", "getValueState: ${e.message}" )
            }
        }
    }

    fun getEmptyState(){
        //api call using coroutines
        viewModelScope.launch {
            try {
                val response = RetrofitClient.getRetrofit().getEmpty()
                _emptyResponse.value = response
            }catch (e: Exception){
                _emptyResponse.value = null
            }
        }
    }
}