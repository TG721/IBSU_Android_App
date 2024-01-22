package com.ibsu.ibsu

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.ibsu.ibsu.databinding.ActivityMainBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.ui.viewmodel.SettingsViewModel
import com.ibsu.ibsu.utils.LanguagesLocale.englishLocale
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale
import com.ibsu.ibsu.utils.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private val settingsPrefsViewModel: SettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDarkMode()
        setTheme(R.style.Base_Theme_IBSU)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }

    private fun setup() {
        setupLanguage()
        setupActionBar()
        setupBottomNavigation()

    }

    private fun setupLanguage() {
        val selectedLanguage = when (settingsPrefsViewModel.getLanguageSetting()) {
            0 -> englishLocale
            1 -> georgianLocale
            else -> getCurrentLocale().language
        }

        val currentLanguage = getCurrentLocale().language
        if (currentLanguage != selectedLanguage) {
            LocaleHelper.setLocale(this@MainActivity, selectedLanguage)
            recreate() // Recreate the activity to apply the new language
        }

    }

    private fun setupDarkMode() {
        when (settingsPrefsViewModel.getDarkModeSetting()) { //0 dark, 1 light, 2 system default
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

    }

    private fun setupActionBar() {
        // Get the color value from a resource file
//        val color = ContextCompat.getColor(applicationContext, R.color.ibsu)
        val toolbar = binding.materialToolbar
        setSupportActionBar(toolbar)
        // Change the background color of the action bar
//        supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
        supportActionBar?.setHomeAsUpIndicator(
            AppCompatResources.getDrawable(this, R.drawable.baseline_arrow_back_24)?.apply {
                setTint(Color.WHITE)
            }
        )
    }

    private fun setupBottomNavigation() {
        bottomNavigationView = binding.bottomNavigationView
        // Initialize the NavController
        navController = binding.fragmentContainerView.getFragment<NavHostFragment>().navController
        setupLabels()
        bottomNavigationView.setOnItemSelectedListener {
            if(!it.isChecked) {
                when (it.itemId) {

                    R.id.homeFragment -> {
                        navController.popBackStack(navController.currentDestination!!.id, true)
                        navController.navigate(R.id.homeFragment)
                        true
                    }

                    R.id.SISFragment -> {
                        navController.popBackStack(navController.currentDestination!!.id, true)
                        navController.navigate(R.id.SISFragment)
                        true
                    }


                    R.id.settingsFragment -> {
                        navController.popBackStack(navController.currentDestination!!.id, true)
                        navController.navigate(R.id.settingsFragment)
                        true
                    }

                    else -> false
                }
            }
            true
        }

    }

    private fun setupLabels() {
        val isDisableLabelOn: Boolean = !settingsPrefsViewModel.getBottomNavBarLabelVisibilitySetting()

        if (isDisableLabelOn) {
            bottomNavigationView?.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_SELECTED);
        } else bottomNavigationView?.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController(R.id.fragmentContainerView).navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
