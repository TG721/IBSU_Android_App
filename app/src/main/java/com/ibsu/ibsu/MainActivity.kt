package com.ibsu.ibsu

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.ibsu.ibsu.databinding.ActivityMainBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.ui.viewmodel.WeekValueViewModel
import com.ibsu.ibsu.utils.LocaleHelper
import com.ibsu.ibsu.utils.ResponseState
import com.ibsu.ibsu.utils.WeekValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private val viewModel: WeekValueViewModel by viewModels()
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
        observeWeekValue();
        setupBottomNavigation();

    }

    private fun setupLanguage() {
        val appSettingPrefs: SharedPreferences =
            getSharedPreferences("appSettingPrefs", Context.MODE_PRIVATE)
        val selectedLanguage = when (appSettingPrefs.getInt("languageVal", 2)) {
            0 -> "en"
            1 -> "ka"
            else -> getCurrentLocale(this).language
        }

        val currentLanguage = getCurrentLocale(this).language
        if (currentLanguage != selectedLanguage) {
            LocaleHelper.setLocale(this@MainActivity, selectedLanguage)
            recreate() // Recreate the activity to apply the new language
        }

    }

    private fun setupDarkMode() {
        val appSettingPrefs: SharedPreferences =
            getSharedPreferences("appSettingPrefs", Context.MODE_PRIVATE)

        when (appSettingPrefs.getInt("darkModeVal", 2)) { //0 dark, 1 light, 2 system default
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
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()

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
                        navController.navigate(R.id.homeFragment)
                        true
                    }

                    R.id.SISFragment -> {
                        navController.navigate(R.id.SISFragment)
                        true
                    }

                    R.id.IniFragment -> {
                        navController.navigate(R.id.iniFragment)
                        true
                    }

                    R.id.settingsFragment -> {
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
        val appSettingPrefs: SharedPreferences = getSharedPreferences("appSettingPrefs", Context.MODE_PRIVATE)
        val isDisableLabelOn: Boolean = appSettingPrefs.getBoolean("disableLabel", false)

        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()


        if (isDisableLabelOn) {
            bottomNavigationView?.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_SELECTED);
        } else bottomNavigationView?.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

    }

    private fun observeWeekValue(): String {
        viewModel.getCurrentWeek();
        var weekValue: String = "";
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myState.collect {
                    when (it) {
                        is ResponseState.Loading -> {
                            setupActionBarText(getString(R.string.loading))
                        }

                        is ResponseState.Error -> {
                            weekValue = getString(R.string.no_week_value_found)
                            WeekValue.weekValue = weekValue
                            setupActionBarText(weekValue)
                        }

                        is ResponseState.Success -> {
                            weekValue = if(getCurrentLocale(this@MainActivity).language=="ka")
                                it.items.weekValueGe
                            else it.items.weekValueEn

                            WeekValue.weekValue = weekValue
                            setupActionBarText(weekValue)
                        }

                        else -> {}
                    }
                }
            }
        }
        return weekValue
    }

    private fun setupActionBarText(weekValue: String) {

        // Change the title color of the action bar
        supportActionBar?.title = HtmlCompat.fromHtml(
            "<font color='#FFFFFF'> IBSU - $weekValue</font>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        //for georgian case
//        supportActionBar?.title = HtmlCompat.fromHtml(
//            "<font color='#FFFFFF'><b> IBSU - $weekValue</b></font>",
//            HtmlCompat.FROM_HTML_MODE_LEGACY
//        )
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
