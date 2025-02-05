package com.srishti.sda.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.srishti.sda.R

 class CatagoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_catagory, container, false)
    }
     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

          val listcat=view.findViewById<ListView>(R.id.catagoryList)

         // Setup category dropdown
         val categories = arrayOf(  "Children","Biographies","Crime","Business & Business","Erotica","Non-Fiction","Fantasy & SciFi","History","Classics","Poetry","Short Stories","Development","Fiction","Language","Thrillers","Teens & Young Adult","Romance","Religion"
         )
         val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, categories)
         listcat.setAdapter(adapter)


     }
}
