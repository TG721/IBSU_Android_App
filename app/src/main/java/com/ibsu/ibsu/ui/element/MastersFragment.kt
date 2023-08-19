package com.ibsu.ibsu.ui.element

import android.content.res.ColorStateList
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentMastersBinding
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MastersFragment(private val schoolValue: String?) : BaseFragment<FragmentMastersBinding>(
    FragmentMastersBinding::inflate
) {
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
            binding.menuProgramsBtn,
            binding.menuAdmissionBtn, binding.menuExamTopicsBtn,
            binding.menuDiscountBtn
        )
    }

    private fun setupContainer() {
        val initialFragment = MasterProgramFragment(schoolValue)
        fragmentContainerID = binding.fragmentMastersContainerView.id
        if (schoolValue != null) {
            binding.programMenu.visibility = View.GONE
        } else binding.programMenu.visibility = View.VISIBLE

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(fragmentContainerID!!, initialFragment)
            .commit()

    }

    override fun listeners() {
        binding.menuAdmissionBtn.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.fragmentMastersContainerView,
                MasterAdmissionFragment()
            ).commit()
            fixColor(menuButtons, it.id);
        }
        binding.menuExamTopicsBtn.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(fragmentContainerID!!, ExamTopicsFragment()).commit()
            fixColor(menuButtons, it.id);
        }
        binding.menuDiscountBtn.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(fragmentContainerID!!, MastersDiscountFragment()).commit()
            fixColor(menuButtons, it.id);
        }
        binding.menuProgramsBtn.setOnClickListener {
            val fragmentTransaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(fragmentContainerID!!, MasterProgramFragment(schoolValue))
                .commit()
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