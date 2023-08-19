package com.ibsu.ibsu.ui.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ibsu.ibsu.ui.element.AdminStaffFragment
import com.ibsu.ibsu.ui.element.LecturersFragment

class ViewPagerForSchoolStaffAdapter(fragment: Fragment, val school: String) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return AdminStaffFragment(school)
            }

            1 -> {
                return LecturersFragment(school)
            }

            else -> {
                throw Resources.NotFoundException("Position not found")
            }
        }
    }

}