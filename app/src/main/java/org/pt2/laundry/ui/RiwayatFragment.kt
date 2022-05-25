package org.pt2.laundry.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import org.pt2.laundry.R
import org.pt2.laundry.databinding.FragmentRiwayatBinding
import org.pt2.laundry.model.Laundry
import org.pt2.laundry.model.Riwayat
import org.pt2.laundry.riwayat.RiwayatAdapter


class RiwayatFragment : Fragment() {

    private lateinit var db: DatabaseReference
    private lateinit var listRiwayat: ListView
    private lateinit var riwayaList: MutableList<Riwayat>
    private lateinit var binding: FragmentRiwayatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateActivity()
        binding = FragmentRiwayatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        db = FirebaseDatabase.getInstance().getReference("Laundry")

        listRiwayat = binding.listRiwayat
        riwayaList = mutableListOf()

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (h in p0.children) {
                        val laundry = h.getValue(Riwayat::class.java)
                        if (laundry != null) {
                            riwayaList.add(laundry)
                        }
                    }

                    val adapter = activity?.let { RiwayatAdapter(it.applicationContext, R.layout.item_riwayat, riwayaList) }
                    listRiwayat.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun onCreateActivity() {
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}