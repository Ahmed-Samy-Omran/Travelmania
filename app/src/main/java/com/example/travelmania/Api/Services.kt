package com.example.travelmania.Api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Services {

    @POST("TravelMania/accounts/register")
    fun register(
        @Body registerResponse: RegisterResponse
    ) :Call<RegisterResponse>


    @POST("TravelMania/accounts/login")
    fun login(
        @Body loginResponse: LoginResponse
    ) :Call<LoginResponse>
}