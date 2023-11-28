package com.ibsu.ibsu.ui.element.student_life

import android.content.res.ColorStateList
import android.util.Log.d
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentSportBinding
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.student_life.SportMenuViewModel
import com.ibsu.ibsu.utils.SportMenu.SportNews
import com.ibsu.ibsu.utils.SportMenu.SportTypes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportFragment :
    BaseFragment<FragmentSportBinding>(FragmentSportBinding::inflate) {
    private lateinit var menuButtons: MutableList<AppCompatButton>
    private var fragmentContainerID: Int? = null
    private lateinit var colorStateListSelected: ColorStateList
    private lateinit var colorStateListNotSelected: ColorStateList
    private val viewModel: SportMenuViewModel by viewModels()

    override fun setup() {
        setupMenuList()
        setupMenuColors()
        setupContainer()
    }

    private fun setupMenuColors() {
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
        val initialFragment: Fragment?
        if (viewModel.getSportMenuActiveItem() == SportTypes) {
            fixColor(menuButtons, R.id.menuSportTypesBtn)
            initialFragment = GamesFragment()
        } else {
            fixColor(menuButtons, R.id.menuSportNewsBtn)
            initialFragment = SportNewsFragment()
        }

        if (binding.fragmentSportContainerView != null)
            fragmentContainerID = binding.fragmentSportContainerView.id

        d("idda", binding.fragmentSportContainerView.toString())
        if (fragmentContainerID != null && binding.fragmentSportContainerView != null)
            childFragmentManager.beginTransaction()
                .replace(fragmentContainerID!!, initialFragment!!)
                .commit()
    }

    override fun listeners() {
        binding.menuSportTypesBtn.setOnClickListener {
            viewModel.setSportMenuActiveItem(SportTypes)

            val fragmentTransaction: FragmentTransaction =
                childFragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.fragmentSportContainerView,
                GamesFragment()
            ).commit()
            fixColor(menuButtons, it.id)
        }
        binding.menuSportNewsBtn.setOnClickListener {
            viewModel.setSportMenuActiveItem(SportNews)

            val fragmentTransaction: FragmentTransaction =
//                requireActivity().supportFragmentManager.beginTransaction()
                childFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentSportContainerView, SportNewsFragment())
                .commit()
            fixColor(menuButtons, it.id)
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