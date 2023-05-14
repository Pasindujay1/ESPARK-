package com.example.espark.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.espark.R
import com.example.espark.models.WarManagerModel

class ActiveItemAdapter(private val activeItemList: ArrayList<WarManagerModel>):RecyclerView.Adapter<ActiveItemAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickLitener

    interface onItemClickLitener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickLitener: onItemClickLitener){
        mListener = clickLitener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val activeItemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)

        return ViewHolder(activeItemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = activeItemList[position]
        holder.txtProductName.text = currentItem.ProName
        holder.txtBrandName.text = currentItem.BraName
        holder.txtPurchDate.text = currentItem.PurchDate

    }

    override fun getItemCount(): Int {
        return activeItemList.size
    }

    class ViewHolder( itemView: View, clickLitener: onItemClickLitener): RecyclerView.ViewHolder(itemView) {

        val txtProductName: TextView = itemView.findViewById(R.id.txtProNameRV)
        val txtBrandName: TextView = itemView.findViewById(R.id.txtBrandNameRV)
        val txtPurchDate: TextView = itemView.findViewById(R.id.txtPurchDateRV)

        init {
            itemView.setOnClickListener {
                clickLitener.onItemClick(adapterPosition)
            }
        }
    }

}