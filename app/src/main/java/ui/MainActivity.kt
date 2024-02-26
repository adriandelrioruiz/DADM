package ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dadm.aidelrio.quotationshake.R
import dadm.aidelrio.quotationshake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_favourites)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}