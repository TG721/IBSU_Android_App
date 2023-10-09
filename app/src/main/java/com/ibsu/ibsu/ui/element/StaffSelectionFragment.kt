package com.ibsu.ibsu.ui.element

import android.content.res.Resources
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentStaffSelectionBinding
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.adapter.ViewPagerForSchoolStaffAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffSelectionFragment :
    BaseFragment<FragmentStaffSelectionBinding>(FragmentStaffSelectionBinding::inflate) {
    private val args by navArgs<StaffSelectionFragmentArgs>()
    private val sharedViewModel: SchoolViewModel by activityViewModels()

    override fun setup() {
        if (args.school != null) {
            sharedViewModel.setSchoolValue(args.school!!)
        }
        sharedViewModel.setEmailVisibility(args.emailVisibility)
        setActionBarName(getString(R.string.staff))
        hideBottomNavigation()
        showBackButton()
        setupTabLayout()
    }

    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager
        val mainViewPagerAdapter = ViewPagerForSchoolStaffAdapter(this, args.school, args.emailVisibility)
        viewPager2.adapter = mainViewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    getString(R.string.administration)
                }

                1 -> {
                    getString(R.string.lecturers)
                }

                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        setActionBarName(getString(R.string.staff))
    }

}