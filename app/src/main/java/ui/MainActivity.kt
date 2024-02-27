package ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import dadm.aidelrio.quotationshake.R
import dadm.aidelrio.quotationshake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MenuProvider{

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        // Añade esta instancia como proveedor de menús a la barra de acción
        addMenuProvider(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_favourites)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.bottomNavigationView as NavigationBarView
        setContentView(binding.root)
        navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        binding.bottomNavigationView.setupWithNavController(navController)
        // establecemos la barra creada como nueva barra de acción
        setSupportActionBar(binding.materialToolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.favouritesFragment, R.id.aboutFragment, R.id.settingsFragment, R.id.newQuotationFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_about, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.aboutFragment)
        {
            navController.navigate(R.id.aboutFragment)
            return true
        }
        return false
    }

}