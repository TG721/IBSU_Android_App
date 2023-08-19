package com.ibsu.ibsu.ui.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ibsu.ibsu.ui.element.BachelorsFragment
import com.ibsu.ibsu.ui.element.DoctorateFragment
import com.ibsu.ibsu.ui.element.MastersFragment


class ViewPagerForProgramsAdapter(fragment: Fragment, schoolValue: String? ) :
    FragmentStateAdapter(fragment) {
    private val fragments: MutableList<Fragment> = mutableListOf()

    init {
        // Preload the fragments for all positions
        fragments.add(BachelorsFragment(schoolValue)) // Position 0
        fragments.add(MastersFragment(schoolValue)) // Position 1
        fragments.add(DoctorateFragment()) // Position 2
    }


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}