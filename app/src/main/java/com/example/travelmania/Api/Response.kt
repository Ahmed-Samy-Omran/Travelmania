package com.example.travelmania.Api

data class RegisterResponse(
	val firstName: String? = null,
	val password: String? = null,
	val phoneNumber: String? = null,
	val age: Int? = null,
	val email: String? = null,
	val lastname: String? = null
)

data class LoginResponse(
	val email: String? = null,
	val password: String? = null,
)

data class CarRentalData(
	val img:Int?=null
)
data class AttractionsData(
	val img:Int?=null
)


