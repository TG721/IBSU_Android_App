package com.ibsu.ibsu.ui.element.iro

import android.content.res.Resources
import com.google.android.material.tabs.TabLayoutMediator
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentExchangeProgramsBinding
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.adapter.ViewPagerForExchangeProgramsAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeProgramsFragment :
    BaseFragment<FragmentExchangeProgramsBinding>(FragmentExchangeProgramsBinding::inflate) {

    override fun setup() {
        setupTabLayout()
        hideBottomNavigation()
        showBackButton()
    }

    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager
        val mainViewPagerAdapter = ViewPagerForExchangeProgramsAdapter(this)
        viewPager2.adapter = mainViewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    "Erasmus+"
                }
                1 -> {
                    "Bilateral"
                }
                2 -> {
                    getString(R.string.virtual)
                }

                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        setActionBarName(getString(R.string.exchange_programs))
    }

}
