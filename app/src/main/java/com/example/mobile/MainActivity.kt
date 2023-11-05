package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.databinding.ActivityMainBinding
import com.example.mobile.uservariables.UserVariables

class MainActivity : AppCompatActivity() {
    lateinit var bindingC : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingC=ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingC.root)
        if(UserVariables.token=="") {
            var intent = Intent(this, SignOrRegistr::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent);
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragmentPlace,StockFragment()).commit()
        bindingC.bMenu.setOnItemSelectedListener {
            when (it.itemId ){
                R.id.quotes->{supportFragmentManager.beginTransaction().replace(R.id.fragmentPlace,StockFragment()).commit()}
                R.id.portfolio->{supportFragmentManager.beginTransaction().replace(R.id.fragmentPlace,PortfolioFragment()).commit()}
                R.id.setting->{supportFragmentManager.beginTransaction().replace(R.id.fragmentPlace,InfoFragment()).commit()}
            }
            true
        }
    }
}