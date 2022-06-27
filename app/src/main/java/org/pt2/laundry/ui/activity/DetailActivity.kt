package org.pt2.laundry.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.firestore.FirebaseFirestore
import org.pt2.laundry.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shimmerFrameLayout = binding.shimmerDetail
        shimmerFrameLayout.startShimmer()

        // Set Databased
        val rootRef = FirebaseFirestore.getInstance()
        val laundryRef = rootRef.collection("laundry")
        laundryRef.whereEqualTo("kodeTransaksi", intent.getStringExtra("kodeT")).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    binding.detailLayout.visibility = View.VISIBLE

                    for (document in it.result!!) {
                        binding.kodeText.text = document.data.getValue("kodeTransaksi").toString()
                        binding.notifText.text = document.data.getValue("status").toString()
                        binding.nameText.text = document.data.getValue("name").toString()
                        binding.alamatText.text = document.data.getValue("address").toString()
                        binding.teleponText.text = document.data.getValue("phoneNumber").toString()
                        val berat = document.data.getValue("weight").toString() + " kg"
                        binding.beratText.text = berat
                        binding.jenisText.text = document.data.getValue("jenisKiloan").toString()
                        val catatan = document.data.getValue("notes").toString()
                        if (catatan == "") {
                            binding.catatanText.text = "-"
                        } else {
                            binding.catatanText.text = document.data.getValue("notes").toString()
                        }
                    }
                }
            }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}