package org.pt2.laundry.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import org.pt2.laundry.databinding.FragmentHomeBinding
import org.pt2.laundry.ui.activity.Kiloan
import org.pt2.laundry.ui.activity.ProfileActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var database: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private lateinit var auth: FirebaseAuth

    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        shimmerFrameLayout = binding.shimmerHome
        shimmerFrameLayout.startShimmer()

        binding.kiloanCard.setOnClickListener {
            activity?.let {
                val intent = Intent(it, Kiloan::class.java)
                it.startActivity(intent)
            }
        }

        binding.profileBtn.setOnClickListener {
            activity?.let {
                val intent = Intent(it, ProfileActivity::class.java)
                it.startActivity(intent)
            }
        }

        val rootRef = FirebaseFirestore.getInstance()
        val laundryRef = rootRef.collection("laundry")

        // Sukses
        laundryRef.whereEqualTo("status", "Success").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {

                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
                binding.homeLayout.visibility = View.VISIBLE

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

                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
                binding.homeLayout.visibility = View.VISIBLE

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

        // Auth Data
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("users")

        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)

        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
                binding.homeLayout.visibility = View.VISIBLE

                binding.idLaundryText.text = snapshot.child("name").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                println("onCancelled")
            }
        })
    }
}