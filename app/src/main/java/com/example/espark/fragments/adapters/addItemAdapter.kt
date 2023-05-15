package com.example.espark.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.espark.R

import com.example.espark.fragments.OnDeleteClickListener
import com.example.espark.fragments.OnUpdateClickListener


class addItemAdapter(

    var additems :List<AddItem>,
    private val onDeleteClickListener: OnDeleteClickListener,
    private val onUpdateClickListener: OnUpdateClickListener,

    ):RecyclerView.Adapter<addItemAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val txtViewItemView:TextView
        val txtViewItemWatt:TextView
        val textViewTime:TextView
        val textViewMinutes:TextView//declare textview
        val btnDelete:ImageView
        val btnUpdate:ImageView//declare textview


        init {//intialize
            txtViewItemView = view.findViewById(R.id.txtViewItemView)
            txtViewItemWatt = view.findViewById(R.id.txtViewItemWatt)
            textViewTime = view.findViewById(R.id.textViewTime)
            textViewMinutes = view.findViewById(R.id.textViewMinutes)
            btnDelete = view.findViewById(R.id.btnDelete)
            btnUpdate = view.findViewById(R.id.btnUpdate)
//            textViewTime = view.findViewById(R.id.)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val addItem = additems[position].itemName + " (" +additems[position].placeUsing + ")"
        holder.txtViewItemView.text = addItem
        holder.txtViewItemWatt.text = additems[position].watt?.toInt().toString()+"W"
        holder.textViewTime.text = additems[position].time + "h"
        holder.textViewMinutes.text = additems[position].minutes + "m"//set values to views
        holder.btnDelete.setOnClickListener{//set onclick listener for delete button
            onDeleteClickListener.onDeleteButtonClicked(position)
        }
        holder.btnUpdate.setOnClickListener{//set onclick listener for update button
            onUpdateClickListener.onUpdateButtonClicked(holder , position)
        }
    }
    override fun getItemCount(): Int {//count items
        return additems.size
    }


}