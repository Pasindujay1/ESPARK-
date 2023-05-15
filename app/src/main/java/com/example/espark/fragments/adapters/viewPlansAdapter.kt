package com.example.espark.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.espark.R
import com.example.espark.fragments.OnDeletePlanClickListener
import com.example.espark.fragments.OnUpdatePlanClickListener


class viewPlansAdapter(
    var planItems :List<Plan>,// a list of Plan objects
    private val onDeleteClickListener: OnDeletePlanClickListener,//listener for delete button click events
    private val onUpdateClickListener: OnUpdatePlanClickListener,// a listener for update button click events
): RecyclerView.Adapter<viewPlansAdapter.ViewHolder>() {

    // ViewHolder class that holds the view references for each item in the list
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val txtViewPlanName:TextView
        val txtViewPrice:TextView
        val btnDelete: ImageView
        val btnUpdate: ImageView

        init {
            txtViewPlanName = view.findViewById(R.id.txtViewItemView)// TextView for plan name
            txtViewPrice = view.findViewById(R.id.txtViewItemWatt)// TextView for plan price
            btnDelete = view.findViewById(R.id.btnDelete)// ImageView for delete button
            btnUpdate = view.findViewById(R.id.btnUpdate)// ImageView for update button


        }



    }
    // inflates the view for the list item and returns a new ViewHolder object
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_plan,parent,false)
        return ViewHolder(view)
    }
    // binds the data of the Plan object at the given position to the views in the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          holder.txtViewPlanName.text = planItems[position].planName//set plan name
          holder.txtViewPrice.text = planItems[position].amount // set plan price
          val name = planItems[position].planName
          holder.btnDelete.setOnClickListener{
              if (name != null) {
                  onDeleteClickListener.onDeleteButtonClicked(name)// handle delete button click event
              }
          }
          holder.btnUpdate.setOnClickListener{
              onUpdateClickListener.onUpdateButtonClicked(holder , position)// handle delete button click event
          }


    }
    override fun getItemCount(): Int {

        return planItems.size
    }
}