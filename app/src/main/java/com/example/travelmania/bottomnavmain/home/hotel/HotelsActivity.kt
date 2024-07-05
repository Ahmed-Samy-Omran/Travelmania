package com.example.travelmania.bottomnavmain.home.hotel

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelmania.R

class HotelsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HotelsActivity", "onCreate called")
        enableEdgeToEdge()
        setContentView(R.layout.activity_hotels)
        val cityId = intent.getIntExtra("cityId", 2)


        val btnSort: ImageView = findViewById(R.id.btn_sort) // Correct R.id usage
        val etSearch: EditText = findViewById(R.id.edit_text_search)
        val btnFilter: ImageView = findViewById(R.id.btn_filter)

        val imgBtnSunrise: ImageView = findViewById(R.id.attraction_img2)
        imgBtnSunrise.setOnClickListener {
            it.animate()
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(100)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        if (animation != null) {
                            super.onAnimationEnd(animation)
                        }
                        it.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(100)
                            .setInterpolator(AccelerateDecelerateInterpolator())
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    val intent = Intent(this@HotelsActivity,HotelsDetailActivity ::class.java)
                                    startActivity(intent)
                                }
                            })
                    }
                })
        }


//        btnFilter.setOnClickListener {
//            it.animate()
//                .scaleX(1.2f)
//                .scaleY(1.2f)
//                .setDuration(100)
//                .withEndAction {
//                    it.animate()
//                        .scaleX(1f)
//                        .scaleY(1f)
//                        .setDuration(100)
//                        .start()
//                }
//                .start()
//            showFilterDialog()
//        }

//        btnSort.setOnClickListener {
//            it.animate()
//                .scaleX(1.2f)
//                .scaleY(1.2f)
//                .setDuration(100)
//                .withEndAction {
//                    it.animate()
//                        .scaleX(1f)
//                        .scaleY(1f)
//                        .setDuration(100)
//                        .start()
//                }
//                .start()
//            showSortDialog()
//        }
//
////        hotelsRecyclerView = findViewById(R.id.hotels_recycler_view)
//        hotelsAdapter = HotelsAdapter(this, hotels)
//        hotelsRecyclerView.adapter = hotelsAdapter
//        hotelsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//
//        val cityId = intent.getIntExtra("cityId", -1)
//        if (cityId != -1) {
//            fetchHotels(cityId)
//        } else {
//            Toast.makeText(this, "Invalid city ID", Toast.LENGTH_SHORT).show()
//        }
//
//        etSearch.addTextChangedListener {
//            val filteredHotels = hotels.filter { it.name.contains(etSearch.text.toString(), ignoreCase = true) }
//            hotelsAdapter.updateHotels(filteredHotels)
//        }
//    }
//
//    private fun showSortDialog() {
//        hotels.sortBy { it.name } // Sort by hotel name
//        hotelsAdapter.notifyDataSetChanged() // Refresh the list
//    }
//
//    override fun onStart() {
//        super.onStart()
//        Log.d("HotelsActivity", "onStart called")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.d("HotelsActivity", "onResume called")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d("HotelsActivity", "onPause called")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d("HotelsActivity", "onStop called")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("HotelsActivity", "onDestroy called")
//    }

//    private fun fetchHotels(cityId: Int) {
//        val retrofit = RetrofitInstance().getRetrofitInstance()
//        val hotelsService = retrofit.create(RetrofitServices::class.java)
//        val call = hotelsService.getHotels(cityId)
//
//        call.enqueue(object : Callback<List<HotelApiItem>> {
//            override fun onResponse(call: Call<List<HotelApiItem>>, response: Response<List<HotelApiItem>>) {
//                if (response.isSuccessful) {
//                    hotels = (response.body() ?: emptyList()).toMutableList()
//                    hotelsAdapter.updateHotels(hotels)
//                } else {
//                    Toast.makeText(this@HotelsActivity, "Failed to load hotels", Toast.LENGTH_SHORT).show()
//                    Log.e("HotelsActivity", "Failed to load hotels: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<List<HotelApiItem>>, t: Throwable) {
//                Toast.makeText(this@HotelsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//                Log.e("HotelsActivity", "Network error: ${t.message}", t)
//            }
//        })
//    }

//    private fun showFilterDialog() {
//        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_filter, null)
//        val rgFilterType = dialogView.findViewById<RadioGroup>(R.id.rg_filter_type)
//        val etFilterName = dialogView.findViewById<EditText>(R.id.et_filter_name)
//        val etFilterRating = dialogView.findViewById<EditText>(R.id.et_filter_rating)
//        val btnApplyFilter = dialogView.findViewById<Button>(R.id.btn_apply_filter)
//
//        val dialog = AlertDialog.Builder(this)
//            .setView(dialogView)
//            .create()
//
//        rgFilterType.setOnCheckedChangeListener { _, checkedId ->
//            when (checkedId) {
//                R.id.rb_filter_name -> {
//                    etFilterName.visibility = View.VISIBLE
//                    etFilterRating.visibility = View.GONE
//                }
//                R.id.rb_filter_rating -> {
//                    etFilterName.visibility = View.GONE
//                    etFilterRating.visibility = View.VISIBLE
//                }
//            }
//        }
//
//        btnApplyFilter.setOnClickListener {
//            val nameFilter = etFilterName.text.toString().trim()
//            val ratingFilterText = etFilterRating.text.toString()
//            val ratingFilter = ratingFilterText.toDoubleOrNull()
//
//            if (ratingFilterText.isNotEmpty() && ratingFilter == null) {
//                Toast.makeText(this, "Please enter a valid rating number", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            val filteredHotels = when (rgFilterType.checkedRadioButtonId) {
//                R.id.rb_filter_name -> hotels.filter { it.name.contains(nameFilter, ignoreCase = true) }
//                R.id.rb_filter_rating -> hotels.filter { it.rate >= (ratingFilter ?: 0.0) }
//                else -> hotels
//            }
//            Log.d("HotelsActivity", "Filtered hotels: $filteredHotels")
//            hotelsAdapter.updateHotels(filteredHotels)
//            dialog.dismiss()
//        }
//
//        dialog.show()
//
        //    }
    }
}