package com.example.esparkapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.espark.activities.ViewItemActivity
import com.example.espark.adapters.ActiveItemAdapter
import com.example.espark.models.WarManagerModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Expired : Fragment() {

    private lateinit var wmRecyclerView: RecyclerView
    private lateinit var wmLoadingData: TextView
    private lateinit var wmActiveList :ArrayList<WarManagerModel>
    private lateinit var dbRef: DatabaseReference


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expired, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wmRecyclerView = view.findViewById(R.id.activeItemsResView)
        wmRecyclerView.layoutManager = LinearLayoutManager(context)//error
        wmRecyclerView.setHasFixedSize(true)
        wmLoadingData = view.findViewById(R.id.txtLoading)

        wmActiveList = arrayListOf<WarManagerModel>()
        getActiveItemList()
    }

    private fun getActiveItemList(){
        wmRecyclerView.visibility = View.GONE
        wmLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Products_WM")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                wmActiveList.clear()
                if(snapshot.exists()){
                    for (activeItemSnap in snapshot.children){
                        val activeItemData = activeItemSnap.getValue(WarManagerModel::class.java)

                        wmActiveList.add(activeItemData!!)
                    }
                    val mAdapter = ActiveItemAdapter(wmActiveList)
                    wmRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : ActiveItemAdapter.onItemClickLitener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireActivity(), ViewItemActivity::class.java)//error

                            //put extras
                            intent.putExtra("wmId", wmActiveList[position].wmId)
                            intent.putExtra("ProName", wmActiveList[position].ProName)
                            intent.putExtra("BraName", wmActiveList[position].BraName)
                            intent.putExtra("PurchDate", wmActiveList[position].PurchDate)
                            intent.putExtra("WarPeriod", wmActiveList[position].WarPeriod)
                            intent.putExtra("Price", wmActiveList[position].Price)
                            intent.putExtra("PurchLocation", wmActiveList[position].PurchLocation)
                            intent.putExtra("PhoneNo", wmActiveList[position].PhoneNo)
                            intent.putExtra("Email", wmActiveList[position].Email)
//                            intent.putExtra("Other", wmActiveList[position].Other)

                            startActivity(intent)
                        }

                    })

                    wmRecyclerView.visibility = View.VISIBLE
                    wmLoadingData.visibility =View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}