package org.pt2.laundry.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import org.pt2.laundry.Kiloan
import org.pt2.laundry.R
import org.pt2.laundry.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var db: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        onCreateActivity()
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        db = FirebaseDatabase.getInstance().getReference("Laundry")

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val count = "+" + p0.getChildrenCount()
                    binding.countProses.text = count.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.kiloan.setOnClickListener {
            activity?.let {
                val intent = Intent(it, Kiloan::class.java)
                it.startActivity(intent)
            }
        }
    }

    fun onCreateActivity() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}