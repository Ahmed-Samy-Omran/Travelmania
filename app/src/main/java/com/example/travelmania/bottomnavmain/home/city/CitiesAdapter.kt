package com.example.ahmed


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.trainig2.CitiesApiItem
import com.example.travelmania.R
import com.example.travelmania.bottomnavmain.home.hotel.HotelsActivity


class CitiesAdapter(private val context: Context,
                    var itemClickListener: (CitiesApiItem) -> Unit,
                    private var cities: List<CitiesApiItem>? = null):
    RecyclerView.Adapter<CitiesAdapter.CitiesItemViewHolder>(){



    class CitiesItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val nameCity: TextView = itemView.findViewById(R.id.category_name_tv)
        val imageCity: ImageView = itemView.findViewById(R.id.cities_img)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return CitiesItemViewHolder(view)
    }

    override fun getItemCount()=cities?.size ?: 0


    override fun onBindViewHolder(holder: CitiesItemViewHolder, position: Int) {

        val city = cities!![position]
        holder.nameCity.text = city.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, HotelsActivity::class.java)
            intent.putExtra("cityId", city.id)
            context.startActivity(intent)
        }

        // Load the image using Glide
        Glide.with(context)
            .load(city.pictureUrl)
            .into(holder.imageCity)

        holder.itemView.setOnClickListener {
            itemClickListener(city)
        }

    }

}