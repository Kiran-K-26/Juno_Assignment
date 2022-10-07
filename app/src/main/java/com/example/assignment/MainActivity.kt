package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Declare main view binding
    private lateinit var mainViewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initialize View binding
        mainViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainViewBinding.root)
        clicks()
    }
    private fun clicks() {
        //Button Click handling and passing state to StateActivity
        mainViewBinding.btnValue.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("state","value")
            val intent = Intent(this,StateActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        mainViewBinding.btnEmpty.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("state","empty")
            val intent = Intent(this,StateActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}