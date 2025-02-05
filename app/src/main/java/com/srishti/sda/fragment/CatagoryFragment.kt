package com.srishti.sda.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.srishti.sda.R
import com.srishti.sda.fragment.UploadFragment
import com.srishti.sda.fragment.fragment_storylist

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
         listcat.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
             val selectedCategory = categories[position]
             Toast.makeText(requireContext(), "Selected: $selectedCategory", Toast.LENGTH_SHORT).show()
             loadFragment(fragment_storylist(), selectedCategory)

         }

     }

  /*  private fun loadFragment(fragment: Fragment, selectedCategory: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }*/
    private fun loadFragment(fragment: Fragment,selectedCategory: String) {
       // val fragment = fragment_storylist()
        val bundle = Bundle()
        bundle.putString("category", selectedCategory)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }



}
