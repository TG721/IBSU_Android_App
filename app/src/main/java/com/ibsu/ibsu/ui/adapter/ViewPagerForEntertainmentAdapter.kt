package com.ibsu.ibsu.ui.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ibsu.ibsu.ui.element.SelfGovernanceFragment
import com.ibsu.ibsu.ui.element.ClubsFragment
import com.ibsu.ibsu.ui.element.SportFragment

class ViewPagerForEntertainmentAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return ClubsFragment()
            }

            1 -> {
                return SportFragment()
            }

            2 -> {
                return SelfGovernanceFragment()
            }

            else -> {
                throw Resources.NotFoundException("Position not found")
            }
        }
    }

}