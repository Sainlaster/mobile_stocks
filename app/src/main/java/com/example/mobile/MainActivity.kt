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
    }
}