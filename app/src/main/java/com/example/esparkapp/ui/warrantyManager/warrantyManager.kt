package com.example.espark.ui.warrantyManager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.espark.Active
import com.example.espark.Expired
import com.example.espark.Expiring
import com.example.espark.R
import com.example.espark.activities.AddProduct
import com.example.espark.databinding.FragmentWarrantyManagerBinding


class warrantyManager : Fragment() {

    private var _binding: FragmentWarrantyManagerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    private var bindingWar: FragmentWarrantyManagerBinding? =null
//    private val binding get() = bindingWar!!

    override fun onCreateView(

//        bindingWar = FragmentWarrantyManagerBinding.inflate(layoutInflater)
//                setContentView(binding.root)
//                replaceFragment(Active())
//
//                bindingWar.bottomNavigationView.setOnItemSelectedListener {
//
//            when(it.itemId){
//
//                R.id.active -> replaceFragment(Active())
//                R.id.expiring -> replaceFragment(Expiring())
//                R.id.expiring -> replaceFragment(Expired())
//
//                else ->{
//
//                }
//            }
//            true
//
//        }
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val warrantyManagerViewModel =
//            ViewModelProvider(this).get(WarrantyManagerViewModel::class.java)

        _binding = FragmentWarrantyManagerBinding.inflate(inflater, container, false)
//        val root: View = binding.root

//        val textView: TextView = binding.textWarrentyManager
//        warrantyManagerViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.active -> replaceFragment1(Active())
                R.id.expiring -> replaceFragment1(Expiring())
                R.id.expired -> replaceFragment1(Expired())
//                R.id.addProduct -> replaceFragment1(AddItem())
            }
            true
        }

        binding.wmToolbar.setOnMenuItemClickListener{ menuItem ->
            when (menuItem.itemId) {
//                R.id.addProduct -> replaceFragment1(AddItem())
                  R.id.addProduct ->{
                      val intent = Intent(activity, AddProduct::class.java)
                      startActivity(intent)
                      true
                  }
                else->false
            }
//            true
        }

        replaceFragment1(Active())
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.addProduct->{replaceFragment(AddItem())
//            true}
//            else -> super.onOptionsItemSelected(item)
//        }


//        return super.onOptionsItemSelected(item)
//    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }



    private fun replaceFragment1(fragment: Fragment){

        childFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()

    }
    //    private fun replaceFragment2(fragment: Fragment){
//        childFragmentManager.beginTransaction()
//            .replace(R.id.warranty_manager, fragment)
//            .commit()
//
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}