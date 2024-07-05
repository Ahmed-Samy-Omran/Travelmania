package com.example.travelmania.bottomnavmain.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ahmed.CitiesAdapter
import com.example.ahmed.RetrofitInstance
import com.example.ahmed.RetrofitServices
import com.example.trainig2.CitiesApiItem
import com.example.travelmania.bottomnavmain.home.chatbot.ChatBot
import com.example.travelmania.bottomnavmain.home.flight.FlightActivity
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.home.taxi.Taxi
import com.example.travelmania.bottomnavmain.home.attractions.AttractionsMainActivity
import com.example.travelmania.bottomnavmain.home.carrental.CarRental
import com.example.travelmania.bottomnavmain.home.hotel.HotelsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment: Fragment(){
    lateinit var linear_flight: LinearLayout
    lateinit var linear_hotel: LinearLayout
    lateinit var linear_car: LinearLayout
    lateinit var linear_taxi: LinearLayout
    lateinit var linear_attractions: LinearLayout
    lateinit var linear_chat_bot: LinearLayout


    private lateinit var citiesRecyclerView: RecyclerView
    private lateinit var citiesAdapter: CitiesAdapter
    private val cities = mutableListOf<CitiesApiItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linear_flight = requireView().findViewById(R.id.linear_flights)
        linear_car = requireView().findViewById(R.id.linear_car_rental)
        linear_taxi = requireView().findViewById(R.id.linear_taxi)
        linear_attractions = requireView().findViewById(R.id.linear_attractions)
        linear_chat_bot = requireView().findViewById(R.id.chatbot_linear)
        linear_hotel = requireView().findViewById(R.id.linear_hotels)

        // Initialize the RecyclerView
        citiesRecyclerView = view.findViewById(R.id.cities_recycler_view)
        citiesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fetchCities()


        linear_flight.setOnClickListener {
            val intent = Intent(context, FlightActivity::class.java)
            startActivity(intent)
        }
        linear_hotel.setOnClickListener {
            val intent = Intent(context, HotelsActivity::class.java)
            startActivity(intent)
        }
        linear_car.setOnClickListener {
            val intent = Intent(context, CarRental::class.java)
            startActivity(intent)
        }
        linear_taxi.setOnClickListener {
            val intent = Intent(context, Taxi::class.java)
            startActivity(intent)
        }
        linear_attractions.setOnClickListener {
            val intent = Intent(context, AttractionsMainActivity::class.java)
            startActivity(intent)
        }
        linear_chat_bot.setOnClickListener {
            val intent = Intent(context, ChatBot::class.java)
            startActivity(intent)
        }
    }
    private fun fetchCities() {
        val retrofit = RetrofitInstance().getRetrofitInstance()
        val citiesService = retrofit.create(RetrofitServices::class.java)
        val call = citiesService.getCities()

        call.enqueue(object : Callback<List<CitiesApiItem>> {
            override fun onResponse(
                call: Call<List<CitiesApiItem>>,
                response: Response<List<CitiesApiItem>>
            ) {
                if (response.isSuccessful) {
                    val cities = response.body() ?: emptyList()
                    citiesAdapter = CitiesAdapter(requireContext(), { city ->
                        val intent = Intent(requireActivity(), HotelsActivity::class.java)
                        intent.putExtra("cityId", city.id)
                        startActivity(intent)
                    }, cities) // Pass the itemClickListener as a lambda
                    citiesRecyclerView.adapter = citiesAdapter
                } else {
                    Toast.makeText(requireContext(), "Failed to load cities", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<CitiesApiItem>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}