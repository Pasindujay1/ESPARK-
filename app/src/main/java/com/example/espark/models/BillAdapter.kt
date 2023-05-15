package com.example.espark.models

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.espark.R
import com.example.espark.UpdateBills
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase

class BillAdapter(context: Context, bills: List<BillData>) : ArrayAdapter<BillData>(context, 0, bills) {

    private val billsList: MutableList<BillData> = bills.toMutableList()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_bill, parent, false)
            holder = ViewHolder()
            holder.yearTextView = view.findViewById(R.id.text_view_year)
            holder.monthTextView = view.findViewById(R.id.text_view_month)
            holder.amountTextView = view.findViewById(R.id.text_view_amount)
            holder.deleteIcon = view.findViewById(R.id.image_view_delete)
            holder.editIcon = view.findViewById(R.id.image_view_update)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val bill = getItem(position)
        val currentItem = billsList[position]

        holder.yearTextView?.text = bill?.billYear
        holder.monthTextView?.text = bill?.billMonth
        holder.amountTextView?.text = bill?.totAmount

        // Get the delete icon and set a click listener to delete the bill
        holder.deleteIcon?.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setTitle("Delete item permanently")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes"){_,_ ->
                    val firebaseRef = FirebaseDatabase.getInstance().getReference("Bills")
                    // realtime database
                    firebaseRef.child(currentItem.billId.toString()).removeValue()
                        .addOnSuccessListener {
                            Toast.makeText(context,"Item removed successfully" ,Toast.LENGTH_SHORT).show()
                            billsList.remove(bill)
                            notifyDataSetChanged()
                        }
                        .addOnFailureListener {error ->
                            Toast.makeText(context,"error ${error.message}" ,Toast.LENGTH_SHORT).show()
                        }
                }
                .setNegativeButton("No"){_,_ ->
                    Toast.makeText(context,"canceled" ,Toast.LENGTH_SHORT).show()
                }
                .show()
        }


        //navigate to Update bills activity when click on edit icon
        holder.editIcon?.setOnClickListener {
            val bill = billsList[position]
            val intent = Intent(context, UpdateBills::class.java)
            intent.putExtra("billId", bill.billId)
            context.startActivity(intent)
        }

        return view!!
    }

    private class ViewHolder {
        var yearTextView: TextView? = null
        var monthTextView: TextView? = null
        var amountTextView: TextView? = null
        var deleteIcon: ImageView? = null
        var editIcon: ImageView? = null
    }

}