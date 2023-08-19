package com.ibsu.ibsu.ui.element

import android.content.res.Resources
import com.google.android.material.tabs.TabLayoutMediator
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentEntertainmentBinding
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.adapter.ViewPagerForEntertainmentAdapter
import com.ibsu.ibsu.ui.adapter.ViewPagerForExchangeProgramsAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
//StudentLifeFragment
class EntertainmentFragment :
    BaseFragment<FragmentEntertainmentBinding>(FragmentEntertainmentBinding::inflate) {

    override fun setup() {
        setupTabLayout()
        setActionBarName(getString(R.string.student_life))
        hideBottomNavigation()
        showBackButton()
    }
    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager
        viewPager2.offscreenPageLimit = 2
        val mainViewPagerAdapter = ViewPagerForEntertainmentAdapter(this)
        viewPager2.adapter = mainViewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    getString(R.string.clubs)
                }
                1 -> {
                    getString(R.string.sport)
                }
                2 -> {
                    getString(R.string.self_governance)
                }

                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        setActionBarName(getString(R.string.student_life))
    }
}