package org.pt2.laundry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigatin_view)
        val navController = findNavController(R.id.fragment)
        val appConfigurations = AppBarConfiguration(setOf(R.id.homeFragment, R.id.riwayatFragment))
        setupActionBarWithNavController(navController, appConfigurations)

        bottomNavigationView.setupWithNavController(navController)

//        supportFragmentManager.commit {
//            add(R.id.cuci, KiloanFragment())
//        }
//        binding.kiloan.setOnClickListener {
//            val intent = Intent(this, Kiloan::class.java)
//            startActivity(intent)
//        }

//        binding.riwayat.setOnClickListener {
//            val intent = Intent(this, Riwayat::class.java)
//            startActivity(intent)
//        }
    }
}