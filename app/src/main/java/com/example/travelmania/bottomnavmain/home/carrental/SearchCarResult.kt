package com.example.travelmania.bottomnavmain.home.carrental

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmania.Api.CarRentalData
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.MainActivity
import java.util.Objects

class SearchCarResult : AppCompatActivity() {
lateinit var recyclerView:RecyclerView
lateinit var back_press:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_car_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
           back_press=findViewById(R.id.car_rental_search_back_press)
        back_press.setOnClickListener {
            val intent=  Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val adapter=CarAdapter(carList)
        recyclerView=findViewById(R.id.recycker_car_rental)
        recyclerView.adapter=adapter
        adapter.onitemclicked=object :CarAdapter.OnItemListClicked{
            override fun onitemCLicked(Position: Int, item: CarRentalData) {
                val intent=Intent(this@SearchCarResult,CarDetailsActivity::class.java)
                startActivity(intent)
            }

        }

    }
    val carList= mutableListOf(CarRentalData(R.drawable.car_1), CarRentalData(R.drawable.car_2)
        ,CarRentalData(R.drawable.car_3))
}