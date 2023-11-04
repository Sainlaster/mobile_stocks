package com.example.mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mobile.databinding.ActivityLoginBinding
import com.example.mobile.databinding.ActivityRegisterBinding
import com.example.mobile.model.ResponserRegister
import com.example.mobile.model.SignInToken
import com.example.mobile.repository.StockServiceRepository
import com.example.mobile.uservariables.UserVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private val stockServiceRepository: StockServiceRepository = StockServiceRepository()
    lateinit var bindingC : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingC=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bindingC.root)
        bindingC.button.setOnClickListener{
            val call = stockServiceRepository.postRegisterUser(bindingC.userName.text.toString(),bindingC.userLogin.text.toString(),bindingC.userPassword.text.toString())
            Log.i("MyLog",bindingC.userName.text.toString())
            Log.i("MyLog",bindingC.userLogin.text.toString())
            Log.i("MyLog",bindingC.userPassword.text.toString())
            call.enqueue(
                object : Callback<ResponserRegister> {
                    override fun onResponse(
                        call: Call<ResponserRegister>,
                        response: Response<ResponserRegister>
                    ) {
                        val responseAnswer: ResponserRegister?=response.body()
                        if (responseAnswer != null) {
                            Log.i("MyLog",responseAnswer.toString())
                            val message = "Регистрация прошла успешно"
                            val duration = Toast.LENGTH_LONG // или Toast.LENGTH_LONG
                            val toast = Toast.makeText(applicationContext, message, duration)
                            toast.show()
                            var intent = Intent(applicationContext, SignOrRegistr::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent);
                        }
                        else
                        {
                            Log.i("MyLog",call.request().toString())
                            Log.i("MyLog",response.message().toString())
                            Log.i("MyLog",response.code().toString())
                            if(response.code()!=200){
                                val message = "Произошла ошибка, мы все исправляем, пожалуйста, подождите"
                                val duration = Toast.LENGTH_LONG // или Toast.LENGTH_LONG
                                val toast = Toast.makeText(applicationContext, message, duration)
                                toast.show()
                            }
                            else{

                            }
                        };
                    }
                    override fun onFailure(call: Call<ResponserRegister>, t: Throwable) {
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
}