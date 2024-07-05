package com.example.travelmania.bottomnavmain.home.attractions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelmania.R

class AttractionHurghadaActivity : AppCompatActivity() {
    lateinit var backBtn: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_attraction_hurghada)

        val btnSort: ImageView = findViewById(R.id.btn_sort) // Correct R.id usage
        val etSearch: EditText = findViewById(R.id.edit_text_search)
        val btnFilter: ImageView = findViewById(R.id.btn_filter)
        btnFilter.setOnClickListener {
            it.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(100)
                .withEndAction {
                    it.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start()
                }
                .start()

        }

        btnSort.setOnClickListener {
            it.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(100)
                .withEndAction {
                    it.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start()
                }
                .start()
        }

        val itemDolphin:ImageView =findViewById(R.id.attraction_img)

//        backBtn.setOnClickListener {
//            it.animate()
//                .scaleX(0.9f)
//                .scaleY(0.9f)
//                .setDuration(100)
//                .setInterpolator(AccelerateDecelerateInterpolator())
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        if (animation != null) {
//                            super.onAnimationEnd(animation)
//                        }
//                        it.animate()
//                            .scaleX(1.0f)
//                            .scaleY(1.0f)
//                            .setDuration(100)
//                            .setInterpolator(AccelerateDecelerateInterpolator())
//                            .setListener(object : AnimatorListenerAdapter() {
//                                override fun onAnimationEnd(animation: Animator) {
//                                    val intent = Intent(this@AttractionHurghadaActivity, AtractionSearchActivity::class.java)
//                                    startActivity(intent)
//                                    finish()
//                                }
//                            })
//                    }
//                })
//        }


        itemDolphin.setOnClickListener {
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
                                    val intent = Intent(this@AttractionHurghadaActivity, AttractionDetailActivity::class.java)
                                    startActivity(intent)
                                }
                            })
                    }
                })
        }
    }
}