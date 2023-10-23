package com.ibsu.ibsu.ui.element.programs

import android.content.res.Resources
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentProgramsBinding
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.adapter.ViewPagerForProgramsAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgramsFragment : BaseFragment<FragmentProgramsBinding>(FragmentProgramsBinding::inflate) {
    private val args by navArgs<ProgramsFragmentArgs>()
    private val sharedViewModel: SchoolViewModel by activityViewModels()


    override fun setup() {
        if (args.school != null) {
            sharedViewModel.setSchoolValue(args.school!!)
        }
        showBackButton()
        setActionBarName(getString(R.string.programs))
        hideBottomNavigation()
        setupTabLayout()
    }


    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager
        viewPager2.offscreenPageLimit = 2
        val mainViewPagerAdapter = ViewPagerForProgramsAdapter(this)
        viewPager2.adapter = mainViewPagerAdapter
//        binding.viewPager.reduceDragSensitivity()

        TabLayoutMediator(tabLayout, viewPager2) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    getString(R.string.bachelor_s)
                }

                1 -> {
                    getString(R.string.master_s)
                }

                2 -> {
                    getString(R.string.doctorate)
                }

                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        if (args.school == null)
            setActionBarName(getString(R.string.programs))
        else setActionBarName(getString(R.string.school_programs))
    }
}