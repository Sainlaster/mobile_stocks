package com.example.mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mobile.databinding.ActivitySignOrRegistrBinding


class SignOrRegistr : AppCompatActivity() {
    lateinit var bindingC : ActivitySignOrRegistrBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingC= ActivitySignOrRegistrBinding.inflate(layoutInflater)
        setContentView(bindingC.root)
    }
    fun signOnClick(view : View){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent);
    }
    fun regOnClick(view :View){
        var intent = Intent(this, Registr::class.java)
        startActivity(intent);
    }
}