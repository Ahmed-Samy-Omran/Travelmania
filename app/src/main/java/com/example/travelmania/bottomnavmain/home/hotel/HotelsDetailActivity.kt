package com.example.travelmania.bottomnavmain.home.hotel

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.home.succes.SuccessActivity
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelsDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private val hotelLatLng = LatLng(27.20972824168748, 33.84609142525378) // Replace with your coordinates
    lateinit var heart_btn:ImageView
    private lateinit var payButton: Button
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var paymentIntentClientSecret: String
    private lateinit var customerConfig: PaymentSheet.CustomerConfiguration

    private val secretKey = "sk_test_51PT8V4RrSgC7Dz4vqBZ8pl1Ps0tsgjFvSwiuBbqRtBjMiduT5K9I8jHfx4VkOnJeSkoR0G39bZkrGfOQvY6VnEbN00QY80WDr3"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hotels_detail)
        initializePaymentConfiguration()
        //map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        heart_btn=findViewById(R.id.btn_favorite)
        heart_btn.setOnClickListener {
            heart_btn.setImageResource(R.drawable.ic_heart_filled)
        }

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        payButton = findViewById(R.id.pay_button)

        // Fetch PaymentIntent and Customer details from your server
        CoroutineScope(Dispatchers.IO).launch {
            createCustomer()
        }



        // Set click listener for the pay button
        payButton.setOnClickListener {
            if (::paymentIntentClientSecret.isInitialized && ::customerConfig.isInitialized) {
                presentPaymentSheet()
            } else {
                Toast.makeText(this, "Payment details not yet loaded", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun initializePaymentConfiguration() {
        val publishableKey = "pk_test_51PT8V4RrSgC7Dz4vuA7UMBzTJpEZiPGFciSIEnrAs5ie94UREnkeuIjDrLBNoJzaIhZh3CZ2kVMkB8uASGSa4IQo00LxZaNNCv" // Replace with your actual publishable key
        PaymentConfiguration.init(applicationContext, publishableKey)    }

    private fun createCustomer() {
        val customerUrl = "https://api.stripe.com/v1/customers"
        customerUrl.httpPost()
            .header("Authorization", "Bearer $secretKey")
            .responseJson { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.e("Stripe", "Error creating customer: ${result.error}")
                    }
                    is Result.Success -> {
                        val responseJson = result.get().obj()
                        val customerId = responseJson.getString("id")
                        createEphemeralKey(customerId)
                    }
                }
            }
    }

    private fun createEphemeralKey(customerId: String) {
        val ephemeralKeyUrl = "https://api.stripe.com/v1/ephemeral_keys?customer=$customerId"
        ephemeralKeyUrl.httpPost()
            .header("Authorization", "Bearer $secretKey")
            .header("Stripe-Version", "2024-04-10") // Ensure to use the latest version
            .responseJson { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.e("Stripe", "Error creating ephemeral key: ${result.error}")
                    }
                    is Result.Success -> {
                        val responseJson = result.get().obj()
                        val ephemeralKey = responseJson.getString("id")
                        createPaymentIntent(customerId, ephemeralKey)
                    }
                }
            }
    }


    private fun createPaymentIntent(customerId: String, ephemeralKey: String) {
        val paymentIntentUrl = "https://api.stripe.com/v1/payment_intents"
        val parameters = listOf(
            "customer" to customerId,
            "amount" to "1099", // Amount in the smallest currency unit (e.g., cents for USD)
            "currency" to "usd",
            "payment_method_types[]" to "card" // Specify the payment method
        )

        paymentIntentUrl.httpPost(parameters)
            .header("Authorization", "Bearer $secretKey")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .responseJson { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.e("Stripe", "Error creating payment intent: ${result.error}")
                    }
                    is Result.Success -> {
                        val responseJson = result.get().obj()
                        paymentIntentClientSecret = responseJson.getString("client_secret")
                        customerConfig = PaymentSheet.CustomerConfiguration(
                            customerId,
                            ephemeralKey
                        )

                        // Initialize PaymentConfiguration here if publishableKey is in the response
                        if (responseJson.has("publishableKey")) {
                            val publishableKey = responseJson.getString("publishableKey")
                            PaymentConfiguration.init(this@HotelsDetailActivity, publishableKey)
                        } else {
                            Log.e("Stripe", "publishableKey missing in the response")
                        }

//                        runOnUiThread {
//                            Toast.makeText(this@HotelsDetailActivity, "Payment info loaded", Toast.LENGTH_SHORT).show()
//                        }
                    }
                }
            }
    }


    private fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "My merchant name",
                customer = customerConfig,
                allowsDelayedPaymentMethods = true // Enable if needed
            )
        )
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Log.d("PaymentSheet", "Canceled")
                showMessage("Payment canceled")

            }
            is PaymentSheetResult.Failed -> {
                Log.e("PaymentSheet", "Error: ${paymentSheetResult.error}")
                showMessage("Payment failed: ${paymentSheetResult.error.message}")
            }
            is PaymentSheetResult.Completed -> {
                Log.d("PaymentSheet", "Completed")
                showMessage("Payment successful!")
                // Navigate to success screen upon successful payment
                navigateToSuccessScreen()
            }
        }
    }
    private fun navigateToSuccessScreen() {
        val intent = Intent(this@HotelsDetailActivity, SuccessActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close current activity to prevent going back to payment screen
    }
    private fun showMessage(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
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