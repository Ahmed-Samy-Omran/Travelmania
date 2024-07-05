package com.example.travelmania.bottomnavmain.home.attractions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelmania.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AttractionDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private val hotelLatLng = LatLng(27.20972824168748, 33.84609142525378) // Replace with your coordinates
    lateinit var backBtn: ImageView
    lateinit var seeAvailability: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_attraction_detail)

        backBtn = findViewById(R.id.btn_back)
        backBtn.setOnClickListener {
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
                                    val intent = Intent(this@AttractionDetailActivity, AttractionHurghadaActivity::class.java)
                                    startActivity(intent)
                                }
                            })
                    }
                })
        }

        seeAvailability = findViewById(R.id.availability_button)
        seeAvailability.setOnClickListener {
            val intent = Intent(this@AttractionDetailActivity,AvailabilityActivity::class.java)
            startActivity(intent)
        }

        //map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker at the hotel's location and move the camera
        mMap.addMarker(MarkerOptions().position(hotelLatLng).title("Hotel Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hotelLatLng, 15f))

        // Set up a click listener to open Google Maps for directions
        mMap.setOnMarkerClickListener { marker ->
            val gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${marker.position.latitude},${marker.position.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
            true
        }

        // Request location permission
        requestLocationPermission()
    }
    private fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted
                enableMyLocation()
            }
            else -> {
                // Request the permission
                requestPermissionLauncher.launch(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                )
            }
        }
    }
    // Register for activity result to request location permissions
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            enableMyLocation()
        }
    }
    // Function to enable MyLocation layer on the map
    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        }
    }

}