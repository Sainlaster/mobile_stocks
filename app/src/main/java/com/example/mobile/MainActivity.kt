package com.example.mobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.databinding.ActivityMainBinding
import com.example.mobile.uservariables.UserVariables

class MainActivity : AppCompatActivity() {
    lateinit var bindingC : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingC=ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingC.root)
        //UserVariables.token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjUyOTkwMDc0NjAsInVzZXJfaWQiOiJmZjBiZjA1MS0wMzJhLTQ1YzktYjkyNy1iZTNjNGZmNmVkZDkifQ.2v4IFjACvDA4m9cgshEyDTgkHQS9M4ZVRrMKZQwAX0I"
        if(UserVariables.token==""){
            var intent = Intent(this, SignOrRegistr::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent);
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragmentPlace,StockFragment()).commit()
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