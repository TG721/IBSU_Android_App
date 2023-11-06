package com.ibsu.ibsu.ui.element

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentSchoolMenuBinding
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolMenuFragment :
    BaseFragment<FragmentSchoolMenuBinding>(FragmentSchoolMenuBinding::inflate) {
    private val args by navArgs<SchoolMenuFragmentArgs>()


    override fun setup() {
        showBackButton()
        hideBottomNavigation()

    }

    override fun onResume() {
        super.onResume()
        setActionBarName(getString(R.string.about_school))
    }

    override fun listeners() {
        binding.apply {
            btnPrograms.setOnClickListener {
                val action =
                    SchoolMenuFragmentDirections.actionSchoolMenuFragmentToProgramsFragment(args.school)
                findNavController().navigate(action)
            }
            btnStaff.setOnClickListener {
                val action =
                    SchoolMenuFragmentDirections.actionSchoolMenuFragmentToStaffSelectionFragment2(
                        args.school,
                        args.emailVisibility
                    )
                findNavController().navigate(action)
            }
        }

    }

}