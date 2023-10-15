package com.ibsu.ibsu.ui.element

import android.app.UiModeManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationBarView
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentSettingsBinding
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private lateinit var sharedPrefsEdit: SharedPreferences.Editor

    override fun setup() {
        setupLabelSwitch()
        setupDarkModeIndicator()
        setupLanguageIndicator()
    }



    private fun setupLanguageIndicator() {
        val appSettingPrefs: SharedPreferences =
            requireContext().getSharedPreferences("appSettingPrefs", Context.MODE_PRIVATE)
        when (appSettingPrefs.getInt("languageVal", SYSTEM_DEFAULT)) { //0 dark, 1 light, 2 system default
            LANGUAGE_ENGLISH -> {
                binding.tvLanguageMore.text = getString(R.string.en)
            }

            LANGUAGE_GEORGIAN -> {
                binding.tvLanguageMore.text = getString(R.string.ka)
            }

            SYSTEM_DEFAULT -> {
                binding.tvLanguageMore.text = getString(R.string.sys)
            }
        }    }

    private fun setupDarkModeIndicator() {
        val appSettingPrefs: SharedPreferences =
            requireContext().getSharedPreferences("appSettingPrefs", Context.MODE_PRIVATE)
        when (appSettingPrefs.getInt("darkModeVal", SYSTEM_DEFAULT)) { //0 dark, 1 light, 2 system default
            MODE_DARK -> {
                binding.tvDarkModeMore.text = getString(R.string.on)
            }

            MODE_LIGHT -> {
                binding.tvDarkModeMore.text = getString(R.string.off)
            }

            SYSTEM_DEFAULT -> {
                binding.tvDarkModeMore.text = getString(R.string.sys)
            }
        }
    }

    private fun setupLabelSwitch() {
        val appSettingPrefs: SharedPreferences = requireContext().getSharedPreferences("appSettingPrefs", Context.MODE_PRIVATE)
        val isDisableLabelOn: Boolean = appSettingPrefs.getBoolean("disableLabel", false)

        sharedPrefsEdit = appSettingPrefs.edit()

        val switchButton = binding.switchBtmNav

        if(isDisableLabelOn) {
            switchButton.isChecked=true
        }

        binding.switchBTMLayout.setOnClickListener {
            switchButton.isChecked=!switchButton.isChecked
            handleBTMSwitchClick()
        }
        binding.darkModeLayout.setOnClickListener{
            handleDarkModeClick()
        }

        binding.languageLayout.setOnClickListener {
            handleLanguageClick()
        }

        switchButton.setOnCheckedChangeListener { _, _ ->
            handleBTMSwitchClick()

        }
    }

    private fun handleLanguageClick() {
        val appSettingPrefs: SharedPreferences = requireContext().getSharedPreferences("appSettingPrefs", Context.MODE_PRIVATE)
        var selectedItemIndex = appSettingPrefs.getInt("languageVal", 2)
        val arrItems = arrayOf(getString(R.string.english), getString(R.string.georgian), getString(R.string.system_default))
        var selectedItem = arrItems[selectedItemIndex]
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.select_language))
            .setSingleChoiceItems(arrItems, selectedItemIndex) { dialog, which ->
                selectedItemIndex = which
                selectedItem = arrItems[which]
            }
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                when(selectedItem){
                    getString(R.string.english) -> {
                        binding.tvLanguageMore.text=getString(R.string.en)
                        sharedPrefsEdit.putInt("languageVal", LANGUAGE_ENGLISH)
                        sharedPrefsEdit.apply()


                    }
                    getString(R.string.georgian) -> {
                        binding.tvLanguageMore.text=getString(R.string.ka)
                        sharedPrefsEdit.putInt("languageVal", LANGUAGE_GEORGIAN)
                        sharedPrefsEdit.apply()

                    }
                    getString(R.string.system_default) -> {
                        binding.tvLanguageMore.text=getString(R.string.sys)
                        sharedPrefsEdit.putInt("languageVal", SYSTEM_DEFAULT)
                        sharedPrefsEdit.apply()//

                    }
                }
                Toast.makeText(requireContext(), getString(R.string.changes_will_apply_after_application_restart), Toast.LENGTH_LONG).show();
            }
            .setNeutralButton(getString(R.string.cancel)) { dialog, which ->

            }.show()
    }

    private fun handleDarkModeClick() {
        val appSettingPrefs: SharedPreferences = requireContext().getSharedPreferences("appSettingPrefs", Context.MODE_PRIVATE)
        var selectedItemIndex = appSettingPrefs.getInt("darkModeVal", 2)
        val arrItems = arrayOf(getString(R.string.dark), getString(R.string.light), getString(R.string.system_default))
        var selectedItem = arrItems[selectedItemIndex]
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.select_mode))
            .setSingleChoiceItems(arrItems, selectedItemIndex) { dialog, which ->
                selectedItemIndex = which
                selectedItem = arrItems[which]
            }
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                when(selectedItem){
                    getString(R.string.dark) -> {
                        binding.tvDarkModeMore.text=getString(R.string.on)
                        sharedPrefsEdit.putInt("darkModeVal", MODE_DARK)
                        sharedPrefsEdit.apply()
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                            val uiModeManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
//                            uiModeManager.setApplicationNightMode(uiModeManager.nightMode)
//                        }
//                        else
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                    }
                    getString(R.string.light) -> {
                        binding.tvDarkModeMore.text=getString(R.string.off)
                        sharedPrefsEdit.putInt("darkModeVal", MODE_LIGHT)
                        sharedPrefsEdit.apply()
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                            val uiModeManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
//                            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO)
//                        }
//                        else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                    }
                    getString(R.string.system_default) -> {
                        binding.tvDarkModeMore.text=getString(R.string.sys)
                        sharedPrefsEdit.putInt("darkModeVal", SYSTEM_DEFAULT)
                        sharedPrefsEdit.apply()//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                            val uiModeManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
//                            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_AUTO)
//                        }
//                        else
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }
            }
            .setNeutralButton(getString(R.string.cancel)) { dialog, which ->

            }.show()
    }

    private fun handleBTMSwitchClick() {
        if (binding.switchBtmNav.isChecked) {
            val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigation?.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_SELECTED);

            sharedPrefsEdit.putBoolean("disableLabel", true)
            sharedPrefsEdit.apply()
        }
        else {
            val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigation?.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
            sharedPrefsEdit.putBoolean("disableLabel", false)
            sharedPrefsEdit.apply()
        }
    }

    companion object {
        const val LANGUAGE_ENGLISH = 0
        const val LANGUAGE_GEORGIAN = 1
        const val SYSTEM_DEFAULT = 2
        const val MODE_DARK = 0
        const val MODE_LIGHT = 1
    }

}