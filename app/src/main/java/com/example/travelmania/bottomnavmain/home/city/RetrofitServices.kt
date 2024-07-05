package com.example.ahmed


import com.example.trainig2.CitiesApiItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitServices {
    @GET("/TravelMania/City")
    fun getCities(): Call<List<CitiesApiItem>>

    @GET("/TravelMania/Hotel/{id}")
    fun getHotels(@Path("id") cityId: Int): Call<List<HotelApiItem>>


}