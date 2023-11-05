package com.example.mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mobile.databinding.ActivityLoginBinding
import com.example.mobile.databinding.ActivityMainBinding
import com.example.mobile.model.SignInToken
import com.example.mobile.model.StockDto
import com.example.mobile.uservariables.UserVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.mobile.repository.StockServiceRepository

class LoginActivity : AppCompatActivity() {
    private val stockServiceRepository: StockServiceRepository = StockServiceRepository()
    lateinit var bindingC : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingC=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingC.root)
    }
    fun onSendClick(view : View){
        val loginButton = findViewById<EditText>(R.id.login)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val call = stockServiceRepository.postSignIn(bindingC.login.text.toString(),bindingC.password.text.toString())
        Log.i("MyLog",loginButton.text.toString())
        Log.i("MyLog",passwordEditText.text.toString())
        call.enqueue(
            object : Callback<SignInToken> {
                override fun onResponse(
                    call: Call<SignInToken>,
                    response: Response<SignInToken>
                ) {
                    val responseAnswer: SignInToken?=response.body()
                    if (responseAnswer != null) {
                        Log.i("MyLog",responseAnswer.toString())
                        UserVariables.token=responseAnswer.token.toString()
                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent);
                    }
                    else
                    {
                        Log.i("MyLog",call.request().toString())
                        Log.i("MyLog",response.message().toString())
                        Log.i("MyLog",response.code().toString())
                        if(response.code()==401){
                            val message = "Неверный логин или пороль"
                            val duration = Toast.LENGTH_LONG // или Toast.LENGTH_LONG
                            val toast = Toast.makeText(applicationContext, message, duration)
                            toast.show()
                            return
                        }
                        if(response.code()!=200){
                            val message = "Произошла ошибка, мы все исправляем, пожалуйста, подождите"
                            val duration = Toast.LENGTH_LONG // или Toast.LENGTH_LONG
                            val toast = Toast.makeText(applicationContext, message, duration)
                            toast.show()
                        }

                    };
                }
                override fun onFailure(call: Call<SignInToken>, t: Throwable) {
                    Log.i("MyLog", t.stackTraceToString())
                    val message = "Произошла ошибка, мы все исправляем, пожалуйста, подождите"
                    val duration = Toast.LENGTH_LONG // или Toast.LENGTH_LONG
                    val toast = Toast.makeText(applicationContext, message, duration)
                    toast.show()
                }
            }
        )
    }
}