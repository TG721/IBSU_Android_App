package com.ibsu.ibsu.ui.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ibsu.ibsu.ui.element.contact.ContactInfoFragment
import com.ibsu.ibsu.ui.element.contact.GoverningBoardFragment
import com.ibsu.ibsu.ui.element.contact.WorkingHoursFragment

class ViewPagerForContactAdapter (fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return ContactInfoFragment()
            }

            1 -> {
                return WorkingHoursFragment()
            }

            2 -> {
                return GoverningBoardFragment()
            }

            else -> {
                throw Resources.NotFoundException("Position not found")
            }
        }
    }

}