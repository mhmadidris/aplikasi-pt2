package org.pt2.laundry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.pt2.laundry.databinding.ActivityKiloanBinding

class Kiloan : AppCompatActivity() {

    private lateinit var binding: ActivityKiloanBinding
    lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKiloanBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.floatingactionbutton.setOnClickListener {
//            val intent = Intent(this, Rincian::class.java)
//            startActivity(intent)

            val intent = Intent(this@Kiloan, Rincian::class.java)
            intent.putExtra("name", binding.nameMember.text.toString())
            intent.putExtra("alamat", binding.alamatMember.text.toString())
            intent.putExtra("phone", binding.phoneMember.text.toString())
            intent.putExtra("weight", binding.weight.text.toString())
            intent.putExtra("notes", binding.notes.text.toString())

            // RadioButton
            val selectedOption: Int = binding.jenis!!.checkedRadioButtonId
            radioButton = findViewById(selectedOption)
            intent.putExtra("jenis", radioButton.text)

            // Price
            when (radioButton.text) {
                "YES" -> {
                    val priceCal = binding.weight.text.toString().toInt()
                    val calculate = priceCal * 8000
                    intent.putExtra("price", calculate.toString())
                }
                "Reguler" -> {
                    val priceCal = binding.weight.text.toString().toInt()
                    val calculate = priceCal * 6000
                    intent.putExtra("price", calculate.toString())
                }
            }
            startActivity(intent)
        }

        val actionbar = supportActionBar
        actionbar!!.title = "Kiloan"
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}