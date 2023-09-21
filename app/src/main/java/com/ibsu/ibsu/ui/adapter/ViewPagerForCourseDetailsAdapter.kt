package com.ibsu.ibsu.ui.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ibsu.ibsu.CoursesFragment
import com.ibsu.ibsu.CreditValueFragment
import com.ibsu.ibsu.ProgramAdminFragment

class ViewPagerForCourseDetailsAdapter(fragment: Fragment, val programVal: String, val type: String) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return CoursesFragment(programVal, type)
            }

            1 -> {
                return CreditValueFragment(programVal, type)
            }

            2 -> {
                return ProgramAdminFragment(programVal, type)
            }

            else -> {
                throw Resources.NotFoundException("Position not found")
            }
        }
    }

}