package com.ibsu.ibsu.ui.element

import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentSchoolSelectionBinding
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.SchoolViewModel
import com.ibsu.ibsu.utils.Schools
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolSelectionFragment : BaseFragment<FragmentSchoolSelectionBinding>(
    FragmentSchoolSelectionBinding::inflate
) {
    override fun setup() {
        setActionBarName(getString(R.string.choose_school))
        hideBottomNavigation()
        showBackButton()
    }

    override fun listeners() {
        binding.apply {
            SchoolOfCompScienceNArchitecturefBtn.setOnClickListener {
                val action = SchoolSelectionFragmentDirections.actionSchoolSelectionFragmentToSchoolMenuFragment3(Schools.computerAndArchitecture)
                findNavController().navigate(action)
            }
            SchoolOfBusinessBtn.setOnClickListener {
                val action = SchoolSelectionFragmentDirections.actionSchoolSelectionFragmentToSchoolMenuFragment3(Schools.business)
                findNavController().navigate(action)
            }
            schoolOfSocialSciBtn.setOnClickListener {
                val action = SchoolSelectionFragmentDirections.actionSchoolSelectionFragmentToSchoolMenuFragment3(Schools.educationHumanitiesAndSocialSciences)
                findNavController().navigate(action)
            }
            SchoolOfLawNStateStaffBtn.setOnClickListener {
                val action = SchoolSelectionFragmentDirections.actionSchoolSelectionFragmentToSchoolMenuFragment3(Schools.lawAndStateGovernance)
                findNavController().navigate(action)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setActionBarName(getString(R.string.choose_school))
    }

}