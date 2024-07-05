package com.example.travelmania.bottomnavmain.home.carrental

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmania.Api.CarRentalData
import com.example.travelmania.R

class CarAdapter(var items:MutableList<CarRentalData>?) :RecyclerView.Adapter<CarAdapter.viewholder>() {
    class viewholder(itemview:View):RecyclerView.ViewHolder(itemview){
         val img_car:ImageView =itemview.findViewById(R.id.img_car_rental)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.car_rental_item,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val item=items!!.get(position)
        holder.img_car.setImageResource(item.img!!)
        holder.img_car.setOnClickListener {
            onitemclicked?.onitemCLicked(position,item)
        }

    }
    interface OnItemListClicked{
        fun onitemCLicked(Position:Int,item: CarRentalData)

    }
    var onitemclicked: OnItemListClicked?=null
    override fun getItemCount(): Int = items?.size ?: 0
    fun changedata(newitems:MutableList<CarRentalData>){
        items=newitems
        notifyDataSetChanged()
    }

}