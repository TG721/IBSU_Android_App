package com.ibsu.ibsu

import android.content.res.Resources
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.ibsu.ibsu.databinding.FragmentProgramDetailsBinding
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.adapter.ViewPagerForCourseDetailsAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgramDetailsFragment :
    BaseFragment<FragmentProgramDetailsBinding>(FragmentProgramDetailsBinding::inflate) {
    private val args by navArgs<ProgramDetailsFragmentArgs>()
    override fun setup() {
        setupTabLayout()
        setActionBarName(getString(R.string.about_program))
        hideBottomNavigation()
        showBackButton()
    }

    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager
        val mainViewPagerAdapter = ViewPagerForCourseDetailsAdapter(this, args.ProgramName)
        viewPager2.adapter = mainViewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    getString(R.string.courses)
                }

                1 -> {
                    getString(R.string.credit_value)
                }

                2 -> {
                    getString(R.string.administration)
                }

                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        setActionBarName(getString(R.string.about_program))
    }
}