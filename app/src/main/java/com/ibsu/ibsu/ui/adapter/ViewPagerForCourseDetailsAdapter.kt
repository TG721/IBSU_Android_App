package com.ibsu.ibsu.ui.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ibsu.ibsu.ui.element.programs.CoursesFragment
import com.ibsu.ibsu.ui.element.programs.CreditValueFragment
import com.ibsu.ibsu.ui.element.programs.ProgramAdminFragment

class ViewPagerForCourseDetailsAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return CoursesFragment()
            }

            1 -> {
                return CreditValueFragment()
            }

            2 -> {
                return ProgramAdminFragment()
            }

            else -> {
                throw Resources.NotFoundException("Position not found")
            }
        }
    }

}