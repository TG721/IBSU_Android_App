package com.ibsu.ibsu.ui.element

import android.content.res.ColorStateList
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentSportBinding
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportFragment :
    BaseFragment<FragmentSportBinding>(FragmentSportBinding::inflate) {
    private lateinit var menuButtons: MutableList<AppCompatButton>
    private var fragmentContainerID: Int? = null
    private lateinit var colorStateListSelected: ColorStateList
    private lateinit var colorStateListNotSelected: ColorStateList
    override fun setup() {
        setupContainer();
        setupMenuList();
        colorStateListSelected =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.ibsu))
        colorStateListNotSelected = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                R.color.program_menu_btn_color
            )
        )
    }

    private fun setupMenuList() {
        menuButtons = mutableListOf(
            binding.menuSportTypesBtn, binding.menuSportNewsBtn
        )
    }

    private fun setupContainer() {
        val initialFragment = GamesFragment()
        fragmentContainerID = binding.fragmentMastersContainerView.id

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(fragmentContainerID!!, initialFragment)
            .commit()
    }

    override fun listeners() {
        binding.menuSportTypesBtn.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.fragmentMastersContainerView,
                GamesFragment()
            ).commit()
            fixColor(menuButtons, it.id);
        }
        binding.menuSportNewsBtn.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(fragmentContainerID!!, SportNewsFragment()).commit()
            fixColor(menuButtons, it.id);
        }

    }

    private fun fixColor(Buttons: MutableList<AppCompatButton>, targetID: Int) {
        for (i in Buttons) {
            if (i.id == targetID) {
                i.backgroundTintList = colorStateListSelected
                i.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                i.backgroundTintList = colorStateListNotSelected
                i.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }
}