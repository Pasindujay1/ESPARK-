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


    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val txtViewPlanName:TextView
        val txtViewPrice:TextView
        val btnDelete: ImageView
        val btnUpdate: ImageView

        init {
            txtViewPlanName = view.findViewById(R.id.txtViewItemView)
            txtViewPrice = view.findViewById(R.id.txtViewItemWatt)
            btnDelete = view.findViewById(R.id.btnDelete)
            btnUpdate = view.findViewById(R.id.btnUpdate)

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_plan,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          holder.txtViewPlanName.text = planItems[position].planName//set plan name
          holder.txtViewPrice.text = planItems[position].amount
          val name = planItems[position].planName
          holder.btnDelete.setOnClickListener{
              if (name != null) {
                  onDeleteClickListener.onDeleteButtonClicked(name)
              }
          }
          holder.btnUpdate.setOnClickListener{
              onUpdateClickListener.onUpdateButtonClicked(holder , position)
          }


    }
    override fun getItemCount(): Int {

        return planItems.size
    }
}