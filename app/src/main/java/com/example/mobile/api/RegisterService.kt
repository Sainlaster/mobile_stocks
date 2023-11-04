package com.example.mobile.api

import com.example.mobile.model.ResponserRegister
import com.example.mobile.model.SignInToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    data class RegisterRequestBody(val userName:String, val login: String, val password: String)
    @POST("internal/register")
    fun postRegister(@Body authorization: RegisterRequestBody): Call<ResponserRegister>
}