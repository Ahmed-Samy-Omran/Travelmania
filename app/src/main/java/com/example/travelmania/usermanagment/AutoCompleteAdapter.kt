package com.example.travelmania.usermanagment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.travelmania.R

class AutoCompleteAdapter(context: Context, resource: Int, objects: Array<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Check if convertView is null, if not, reuse it; otherwise, inflate a new view
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.dropdown_item, parent, false)

        val textViewDropdownItem: TextView = view.findViewById(R.id.textViewDropdownItem)
        textViewDropdownItem.text = getItem(position)?.substringBefore(" ")

        return view
    }
}