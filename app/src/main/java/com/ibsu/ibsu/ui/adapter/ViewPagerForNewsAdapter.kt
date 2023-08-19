package com.ibsu.ibsu.ui.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ibsu.ibsu.ui.element.AnnouncementsFragment
import com.ibsu.ibsu.ui.element.BilateralFragment
import com.ibsu.ibsu.ui.element.ErasmusPlusFragment
import com.ibsu.ibsu.ui.element.EventsFragment
import com.ibsu.ibsu.ui.element.NewsFragment
import com.ibsu.ibsu.ui.element.OnlineFragment

class ViewPagerForNewsAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return EventsFragment()
            }

            1 -> {
                return NewsFragment()
            }

            2 -> {
                return AnnouncementsFragment()
            }

            else -> {
                throw Resources.NotFoundException("Position not found")
            }
        }
    }

}