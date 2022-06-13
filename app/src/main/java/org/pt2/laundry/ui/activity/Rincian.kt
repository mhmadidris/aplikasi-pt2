package org.pt2.laundry.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import org.pt2.laundry.databinding.ActivityRincianBinding
import kotlin.random.Random

class Rincian : AppCompatActivity() {
    private lateinit var binding: ActivityRincianBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRincianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = "Rincian"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val rnds = "LD-" + Random.nextInt(10, 1000000).toString()
        binding.nomorLaundryDisplay.text = rnds
        binding.nameDisplay.text = intent.getStringExtra("name")
        binding.alamatDisplay.text = intent.getStringExtra("alamat")
        binding.phoneDisplay.text = intent.getStringExtra("phone")
        binding.weightDisplay.text = intent.getStringExtra("weight")
        if (intent.getStringExtra("notes").toString().isNotEmpty()) {
            binding.notesDisplay.text = intent.getStringExtra("notes")
        } else {
            binding.notesDisplay.text = "-"
        }
        binding.jenisDisplay.text = intent.getStringExtra("jenis")
        binding.priceDisplay.text = intent.getStringExtra("price")

        binding.bottomNavigatinView.setOnClickListener {
            saveData()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun saveData() {
        val kodeTransaction: String = binding.nomorLaundryDisplay.text as String
        val name: String = binding.nameDisplay.text as String
        val alamat: String = binding.alamatDisplay.text as String
        val phone: String = binding.phoneDisplay.text as String
        val weight: CharSequence? = binding.weightDisplay.text
        val notes: String = binding.notesDisplay.text as String
        val jenis: String = binding.jenisDisplay.text as String
        val status = "Proses"
        val totalPrice: CharSequence? = binding.priceDisplay.text

        val db = FirebaseFirestore.getInstance()
        val laundry: MutableMap<String, Any> = HashMap()
        laundry["kodeTransaksi"] = kodeTransaction
        laundry["name"] = name
        laundry["address"] = alamat
        laundry["phoneNumber"] = phone
        laundry["weight"] = weight.toString().toInt()
        laundry["notes"] = notes
        laundry["jenisKiloan"] = jenis
        laundry["status"] = status
        laundry["totalPrice"] = totalPrice.toString().toInt()

        db.collection("laundry").add(laundry)
            .addOnSuccessListener {
                Toast.makeText(this@Rincian, "Berhasil", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@Rincian, "Gagal", Toast.LENGTH_SHORT).show()
            }
    }
}