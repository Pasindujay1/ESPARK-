package com.example.espark.fragments


import com.example.espark.fragments.adapters.addItemAdapter
import com.example.espark.fragments.adapters.viewPlansAdapter


interface OnDeleteClickListener {
    fun onDeleteButtonClicked(position: Int)
}
interface OnDeletePlanClickListener {
    fun onDeleteButtonClicked(name :String )
}
interface OnUpdateClickListener {
    fun onUpdateButtonClicked(viewHolder: addItemAdapter.ViewHolder, position: Int)
}
interface OnUpdatePlanClickListener {
    fun onUpdateButtonClicked(viewHolder: viewPlansAdapter.ViewHolder,position: Int)
}

