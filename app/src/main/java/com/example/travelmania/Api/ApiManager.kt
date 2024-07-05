package com.example.travelmania.Api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    companion object{
        private const val BASE_URL = "http://travelmania.runasp.net/"
        private var retrofit: Retrofit?=null
        private fun getinstance(): Retrofit {

            if(retrofit==null){
                //create retrofit

                val logging= HttpLoggingInterceptor(
                    object : HttpLoggingInterceptor.Logger{
                        override fun log(message: String) {
                            Log.e("api",message)
                        }

                    }
                )
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client= OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

                retrofit= Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
        fun getApis():Services{
            return getinstance().create(Services::class.java)
        }
    }
}