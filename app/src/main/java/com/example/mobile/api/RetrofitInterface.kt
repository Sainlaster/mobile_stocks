package com.example.mobile.api
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Field
object RetrofitInterface {
    private const val BASE_URL = "http://mock-stock-service.ru/api/v1/"
    private val retrofit by lazy {
        val gson = GsonBuilder()
            .setLenient()
            .setExclusionStrategies(
                object: ExclusionStrategy {
                    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                        return false
                    }

                    override fun shouldSkipField(f: FieldAttributes?): Boolean {
                        val fieldName = f?.name
                        val theClass = f?.declaringClass
                        return isFieldInSuperclass(theClass, fieldName)
                    }

                    private fun isFieldInSuperclass(subclass: Class<*>?, fieldName: String?): Boolean {
                        var superclass: Class<*>? = subclass?.superclass
                        var field: Field?

                        while (superclass != null) {
                            field = getField(superclass, fieldName)

                            if (field != null)
                                return true

                            superclass = superclass.superclass
                        }

                        return false
                    }

                    private fun getField(theClass: Class<*>, fieldName: String?): Field? {
                        return try {
                            theClass.getDeclaredField(fieldName)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }
            )
            .create()
        val httpClient = OkHttpClient.Builder()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    val stockApi: StockService by lazy {
        retrofit.create(StockService::class.java)
    }
    val stockByIdApi: StockIdService by lazy {
        retrofit.create(StockIdService::class.java)
    }
    val signInApi: SignInService by lazy {
        retrofit.create(SignInService::class.java)
    }
    val stockMyApi: PortfolioStockService by lazy {
        retrofit.create(PortfolioStockService::class.java)
    }
    val stockBuy: BuyStockService by lazy{
        retrofit.create(BuyStockService::class.java)
    }
}