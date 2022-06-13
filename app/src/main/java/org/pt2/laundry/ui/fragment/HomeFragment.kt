package org.pt2.laundry.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import org.pt2.laundry.databinding.FragmentHomeBinding
import org.pt2.laundry.ui.activity.Kiloan

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.kiloanCard.setOnClickListener {
            activity?.let {
                val intent = Intent(it, Kiloan::class.java)
                it.startActivity(intent)
            }
        }

        val rootRef = FirebaseFirestore.getInstance()
        val laundryRef = rootRef.collection("laundry")

        // Sukses
        laundryRef.whereEqualTo("status", "Success").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var count = 0
                task.result?.let {
                    for (snapshot in it) {
                        count++
                    }
                }
                val total = "+$count"
                binding.countSuccess.text = total
            } else {
                task.exception?.message?.let {
                    print(it)
                }
            }
        }

        // Proses
        laundryRef.whereEqualTo("status", "Proses").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var count = 0
                task.result?.let {
                    for (snapshot in it) {
                        count++
                    }
                }
                val total = "+$count"
                binding.countProses.text = total
            } else {
                task.exception?.message?.let {
                    print(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}