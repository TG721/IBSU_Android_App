package com.ibsu.ibsu.ui.element

import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentMenuNewsBinding
import com.ibsu.ibsu.ui.adapter.ViewPagerForNewsAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuNewsFragment : BaseFragment<FragmentMenuNewsBinding>(FragmentMenuNewsBinding::inflate) {

    override fun setup() {
        setupTabLayout()
    }


    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager
        val mainViewPagerAdapter = ViewPagerForNewsAdapter(this)
        viewPager2.adapter = mainViewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    getString(R.string.upcoming_events)
                }

                1 -> {
                    getString(R.string.news)
                }

                2 -> {
                    getString(R.string.announcements)
                }

                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()
    }

}