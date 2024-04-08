package dadm.aidelrio.quotationshake.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import dadm.aidelrio.quotationshake.R
import dadm.aidelrio.quotationshake.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MenuProvider{

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Añade esta instancia como proveedor de menús a la barra de acción
        addMenuProvider(this)
        setContentView(R.layout.fragment_favourites)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.bottomNavigationView as NavigationBarView
        setContentView(binding.root)
        navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        binding.bottomNavigationView.setupWithNavController(navController)
        // establecemos la barra creada como nueva barra de acción
        setSupportActionBar(binding.materialToolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.favouritesFragment, R.id.aboutFragment, R.id.settingsFragment, R.id.swipeToRefresh))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_about, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.aboutItem)
        {
            navController.navigate(R.id.aboutFragment)
            return true
        }
        return false
    }

}