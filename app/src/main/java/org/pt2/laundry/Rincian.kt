package org.pt2.laundry

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.pt2.laundry.databinding.ActivityRincianBinding
import org.pt2.laundry.model.Laundry
import org.pt2.laundry.model.Riwayat

class Rincian : AppCompatActivity() {
    private lateinit var binding: ActivityRincianBinding

    private lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRincianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ref = FirebaseDatabase.getInstance().getReference("Laundry")

        val actionbar = supportActionBar
        actionbar!!.title = "Rincian"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val rnds = "LD-" + (0..1000000).random()
        binding.nomorLaundryDisplay.text = rnds.toString()
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

        binding.bottomNavigatinView.setOnClickListener{
            saveData()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun saveData() {
        val kodeTransaction : String = binding.nomorLaundryDisplay.text as String
        val name : String = binding.nameDisplay.text as String
        val alamat : String = binding.alamatDisplay.text as String
        val phone : String = binding.phoneDisplay.text as String
        val weight : CharSequence? = binding.weightDisplay.text
        val notes : String = binding.notesDisplay.text as String
        val jenis : String = binding.jenisDisplay.text as String
        val status : String = "Proses"
        val totalPrice : CharSequence? = binding.priceDisplay.text

        val ldId : String? = ref.push().key

        val ld = ldId?.let { Laundry(it, kodeTransaction, name, alamat, phone, weight, notes, jenis, status, totalPrice) }

        if (ldId != null) {
            ref.child(ldId).setValue(ld).addOnCompleteListener{
                Toast.makeText(applicationContext, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}