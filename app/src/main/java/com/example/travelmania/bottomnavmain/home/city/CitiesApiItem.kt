package com.example.trainig2

import com.google.gson.annotations.SerializedName

data class CitiesApiItem(
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("pictureUrl")
    val pictureUrl: String
)