package org.pt2.laundry.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.pt2.laundry.R
import org.pt2.laundry.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var auth: FirebaseAuth
    private var databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("users")

        binding.loginText.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.signupButton.setOnClickListener {
            signup()
        }

        // Back Button
        binding.backBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun signup() {
        if (binding.name.toString().isEmpty()) {
            Toast.makeText(this.context, "Masukkan nama anda", Toast.LENGTH_SHORT).show()
        } else if (binding.email.toString().isEmpty()) {
            Toast.makeText(this.context, "Masukkan email anda", Toast.LENGTH_SHORT).show()
        } else if (binding.password.toString().isEmpty()) {
            Toast.makeText(this.context, "Masukkan password anda", Toast.LENGTH_SHORT).show()
        }

        auth.createUserWithEmailAndPassword(
            binding.email.text.toString(),
            binding.password.text.toString()
        )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUser = auth.currentUser
                    val currentUserDb = databaseReference?.child(currentUser?.uid!!)
                    currentUserDb?.child("name")?.setValue(binding.name.text.toString())

                    view?.let { it1 ->
                        Navigation.findNavController(it1).navigate(R.id.loginFragment)
                    }

                    Toast.makeText(
                        this@SignupFragment.context,
                        "Selamat datang",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@SignupFragment.context,
                        "Registrasi gagal",
                        Toast.LENGTH_SHORT
                    ).show()
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