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
import org.pt2.laundry.R
import org.pt2.laundry.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.signupText.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.signupFragment)
        }

        // Session
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Navigation.findNavController(view).navigate(R.id.homeFragment)
        }

        binding.loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (binding.email.toString().isEmpty()) {
            Toast.makeText(this.context, "Masukkan email anda", Toast.LENGTH_SHORT).show()
        } else if (binding.password.toString().isEmpty()) {
            Toast.makeText(this.context, "Masukkan password anda", Toast.LENGTH_SHORT).show()
        }

        auth.signInWithEmailAndPassword(
            binding.email.text.toString(),
            binding.password.text.toString()
        )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    view?.let { it1 ->
                        Navigation.findNavController(it1).navigate(R.id.homeFragment)
                    }
                } else {
                    Toast.makeText(this@LoginFragment.context, "Login failed", Toast.LENGTH_SHORT)
                        .show()
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