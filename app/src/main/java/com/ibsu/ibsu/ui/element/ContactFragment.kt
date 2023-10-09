package com.ibsu.ibsu.ui.element

import android.content.res.Resources
import com.google.android.material.tabs.TabLayoutMediator
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentContactBinding
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.reduceDragSensitivity
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.adapter.ViewPagerForContactAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactFragment : BaseFragment<FragmentContactBinding>(FragmentContactBinding::inflate) {

    override fun setup() {
        showBackButton()
        setActionBarName(getString(R.string.contact))
        hideBottomNavigation()
        setupTabLayout()
    }

    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager
        viewPager2.offscreenPageLimit = 3 // Preload all three fragments
        val mainViewPagerAdapter = ViewPagerForContactAdapter(this)
        viewPager2.adapter = mainViewPagerAdapter


        TabLayoutMediator(tabLayout, viewPager2) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    getString(R.string.contact_information)
                }

                1 -> {
                    getString(R.string.working_hours)
                }

                2 -> {
                    getString(R.string.governing_board)
                }

                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        setActionBarName(getString(R.string.contact))
    }
}