package com.example.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.mobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bindingC : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingC=ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingC.root)

        bindingC.bMenu.setOnItemSelectedListener {
            when (it.itemId ){
                R.id.quotes->{supportFragmentManager.beginTransaction().replace(R.id.fragmentPlace,StockFragment()).commit()}
                R.id.portfolio->{supportFragmentManager.beginTransaction().replace(R.id.fragmentPlace,PortfolioFragment()).commit()}
            }
            true
        }
    }
    fun OnNavButtonClick(){
    }
}