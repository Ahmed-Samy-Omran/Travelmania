package com.example.travelmania.bottomnavmain.home.attractions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.home.succes.SuccessActivity
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AvailabilityActivity : AppCompatActivity() {
    private lateinit var spinnerDate: Spinner
    private lateinit var textViewTime: TextView
    private lateinit var dateToTimeMap: HashMap<String, String>
    private lateinit var spinnerLanguage: Spinner
    private lateinit var languages: Array<String>

    //counter
    private var infantCount = 0
    private var adultsCount = 0
    private var childrenCount = 0

    private lateinit var infantCountTextView: TextView
    private lateinit var adultsCountTextView: TextView
    private lateinit var childrenCountTextView: TextView

    private lateinit var plusButtonAdult: ImageView
    private lateinit var plusButtonChild: ImageView
    private lateinit var plusButtonInfenat: ImageView

    private lateinit var minusButtonAdult: ImageView
    private lateinit var minusButtonChild: ImageView
    private lateinit var minusButtonInfenat: ImageView


    private lateinit var totalPriceTextView: TextView
    lateinit var backBtn: LinearLayout
    //payment
    private lateinit var payButton: Button
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var paymentIntentClientSecret: String
    private lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    private val secretKey = "sk_test_51PT8V4RrSgC7Dz4vqBZ8pl1Ps0tsgjFvSwiuBbqRtBjMiduT5K9I8jHfx4VkOnJeSkoR0G39bZkrGfOQvY6VnEbN00QY80WDr3"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_availability)

        //payment initilize
        initializePaymentConfiguration()

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        payButton = findViewById(R.id.pay_btn)

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
        // Initialize views
        infantCountTextView = findViewById(R.id.infant_tv)
        adultsCountTextView = findViewById(R.id.adult_tv)
        childrenCountTextView =findViewById(R.id.child_tv)

        plusButtonAdult = findViewById(R.id.buttonPlusAdult)
        plusButtonChild = findViewById(R.id.buttonPlusChild)
        plusButtonInfenat = findViewById(R.id.buttonPlusInfant)


        minusButtonAdult = findViewById(R.id.buttonMinusAdult)
        minusButtonChild = findViewById(R.id.buttonMinusChild)
        minusButtonInfenat = findViewById(R.id.buttonMinusInfant)

        totalPriceTextView = findViewById(R.id.totalPriceTextView)

        backBtn = findViewById(R.id.back_press)
        backBtn.setOnClickListener {
            it.animate()
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(100)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        it.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(100)
                            .setInterpolator(AccelerateDecelerateInterpolator())
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    val intent = Intent(this@AvailabilityActivity, AttractionDetailActivity::class.java)
                                    startActivity(intent)
                                }
                            })
                    }
                })
        }



        // Set initial values
        updateInfantCount()
        updateAdultsCount()
        updateChildrenCount()
        updateTotalPrice()

        // Set up click listeners

        plusButtonInfenat.setOnClickListener {
            infantCount += 1
            updateInfantCount()
            updateTotalPrice()
        }

        minusButtonInfenat.setOnClickListener {
            if (infantCount > 0) {
                infantCount -= 1
                updateInfantCount()
                updateTotalPrice()
            }
        }

        plusButtonAdult.setOnClickListener {
            adultsCount += 1
            updateAdultsCount()
            updateTotalPrice()
        }

        minusButtonAdult.setOnClickListener {
            if (adultsCount > 0) {
                adultsCount -= 1
                updateAdultsCount()
                updateTotalPrice()
            }
        }

        plusButtonChild.setOnClickListener {
            childrenCount += 1
            updateChildrenCount()
            updateTotalPrice()
        }

        minusButtonChild.setOnClickListener {
            if (childrenCount > 0) {
                childrenCount -= 1
                updateChildrenCount()
                updateTotalPrice()
            }
        }



        spinnerDate = findViewById(R.id.spinnerDate)
        textViewTime = findViewById(R.id.textViewTime)
        spinnerLanguage = findViewById(R.id.spinner)

        // Populate data
        dateToTimeMap = hashMapOf(
            "Wed 26 Jun 2024" to "Start at 3:00 am",
            "Thu 27 Jun 2024" to "Start at 7:00 am",
            "Fri 28 Jun 2024" to "Start at 10:00 am"
        )

        // Create list of dates
        val dates = dateToTimeMap.keys.toTypedArray()

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dates)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDate.adapter = adapter

        // Set the Spinner's onItemSelectedListener
        spinnerDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedDate = parent.getItemAtPosition(position) as String
                textViewTime.text = dateToTimeMap[selectedDate]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // second spinner
        // Populate data for languages
        languages = arrayOf(
            "English - Tour guide",
            "Arabic - Tour guide",
            "Spanish - Tour guide",
            "French - Tour guide",
            "German - Tour guide",
            "Chinese - Tour guide",
            "Japanese - Tour guide",
            "Russian - Tour guide",
            "Portuguese - Tour guide",
            "Italian - Tour guide",
            "Korean - Tour guide"
        )

        // Create an ArrayAdapter using the string array and a default spinner layout
        val languageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.adapter = languageAdapter

        // Set the Spinner's onItemSelectedListener for languages
        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedLanguage = parent.getItemAtPosition(position) as String
                // You can perform an action based on the selected language if needed
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
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
                            PaymentConfiguration.init(this@AvailabilityActivity, publishableKey)
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
        val intent = Intent(this@AvailabilityActivity, SuccessActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close current activity to prevent going back to payment screen
    }
    private fun showMessage(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateInfantCount() {
        infantCountTextView.text = infantCount.toString()
    }

    private fun updateAdultsCount() {
        adultsCountTextView.text = adultsCount.toString()
    }

    private fun updateChildrenCount() {
        childrenCountTextView.text = childrenCount.toString()
    }
    private fun updateTotalPrice() {
        val adultPrice = 17
        val childPrice = 9
        val infantPrice = 3

        val totalPrice = (adultsCount * adultPrice) + (childrenCount * childPrice) + (infantCount * infantPrice)
        totalPriceTextView.text = "$$totalPrice"
    }
}