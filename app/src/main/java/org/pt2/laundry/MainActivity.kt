package org.pt2.laundry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.pt2.laundry.ui.fragment.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.fragment)
        val appConfigurations = AppBarConfiguration(setOf(R.id.homeFragment, R.id.riwayatFragment))
        setupActionBarWithNavController(navController, appConfigurations)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Bottom Bar
            if (destination.id == R.id.loginFragment || destination.id == R.id.signupFragment) {
                bottomNavigationView.visibility = View.GONE
            } else {

                bottomNavigationView.visibility = View.VISIBLE
            }
        }
        bottomNavigationView.setupWithNavController(navController)
    }
}