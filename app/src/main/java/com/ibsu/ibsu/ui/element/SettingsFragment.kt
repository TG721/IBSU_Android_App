package com.ibsu.ibsu.ui.element

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationBarView
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentSettingsBinding
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private val settingsPrefsViewModel: SettingsViewModel by viewModels()

    override fun setup() {
        setupLabelSwitch()
        setupDarkModeIndicator()
        setupLanguageIndicator()
    }


    private fun setupLanguageIndicator() {
        when (settingsPrefsViewModel.getLanguageSetting()) { //0 dark, 1 light, 2 system default
            LANGUAGE_ENGLISH -> {
                binding.tvLanguageMore.text = getString(R.string.en)
            }

            LANGUAGE_GEORGIAN -> {
                binding.tvLanguageMore.text = getString(R.string.ka)
            }

            SYSTEM_DEFAULT -> {
                binding.tvLanguageMore.text = getString(R.string.sys)
            }
        }
    }

    private fun setupDarkModeIndicator() {
        when (settingsPrefsViewModel.getDarkModeSetting()) { //0 dark, 1 light, 2 system default
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

        val isDisableLabelOn: Boolean = !settingsPrefsViewModel.getBottomNavBarLabelVisibilitySetting()


        val switchButton = binding.switchBtmNav

        if (isDisableLabelOn) {
            switchButton.isChecked = true
        }

        binding.switchBTMLayout.setOnClickListener {
            switchButton.isChecked = !switchButton.isChecked
            handleBTMSwitchClick()
        }
        binding.darkModeLayout.setOnClickListener {
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
        val appSettingPrefs: SharedPreferences =
            requireContext().getSharedPreferences("appSettingPrefs", Context.MODE_PRIVATE)
        var selectedItemIndex = appSettingPrefs.getInt("languageVal", 2)
        val arrItems = arrayOf(
            getString(R.string.english),
            getString(R.string.georgian),
            getString(R.string.system_default)
        )
        var selectedItem = arrItems[selectedItemIndex]
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.select_language))
            .setSingleChoiceItems(arrItems, selectedItemIndex) { dialog, which ->
                selectedItemIndex = which
                selectedItem = arrItems[which]
            }
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                when (selectedItem) {
                    getString(R.string.english) -> {
                        binding.tvLanguageMore.text = getString(R.string.en)
                        settingsPrefsViewModel.setLanguageSetting(LANGUAGE_ENGLISH)

                    }

                    getString(R.string.georgian) -> {
                        binding.tvLanguageMore.text = getString(R.string.ka)
                        settingsPrefsViewModel.setLanguageSetting(LANGUAGE_GEORGIAN)

                    }

                    getString(R.string.system_default) -> {
                        binding.tvLanguageMore.text = getString(R.string.sys)
                        settingsPrefsViewModel.setLanguageSetting(SYSTEM_DEFAULT)

                    }
                }
                Toast.makeText(
                    requireContext(),
                    getString(R.string.changes_will_apply_after_application_restart),
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNeutralButton(getString(R.string.cancel)) { dialog, which ->

            }.show()
    }

    private fun handleDarkModeClick() {
        var selectedItemIndex = settingsPrefsViewModel.getDarkModeSetting()
        val arrItems = arrayOf(
            getString(R.string.dark),
            getString(R.string.light),
            getString(R.string.system_default)
        )
        var selectedItem = arrItems[selectedItemIndex]
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.select_mode))
            .setSingleChoiceItems(arrItems, selectedItemIndex) { dialog, which ->
                selectedItemIndex = which
                selectedItem = arrItems[which]
            }
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                when (selectedItem) {
                    getString(R.string.dark) -> {
                        binding.tvDarkModeMore.text = getString(R.string.on)
                        settingsPrefsViewModel.setDarkModeSetting(MODE_DARK)
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                            val uiModeManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
//                            uiModeManager.setApplicationNightMode(uiModeManager.nightMode)
//                        }
//                        else
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                    }

                    getString(R.string.light) -> {
                        binding.tvDarkModeMore.text = getString(R.string.off)
                        settingsPrefsViewModel.setDarkModeSetting(MODE_LIGHT)
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                            val uiModeManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
//                            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO)
//                        }
//                        else
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                    }

                    getString(R.string.system_default) -> {
                        binding.tvDarkModeMore.text = getString(R.string.sys)
                        settingsPrefsViewModel.setDarkModeSetting(SYSTEM_DEFAULT)
                        //                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
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
            val bottomNavigation =
                activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigation?.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_SELECTED)

            settingsPrefsViewModel.setBottomNavBarLabelVisibilitySetting(true)
        } else {
            val bottomNavigation =
                activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigation?.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED)
            settingsPrefsViewModel.setBottomNavBarLabelVisibilitySetting(false)
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