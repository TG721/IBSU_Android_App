package com.ibsu.ibsu.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ibsu.ibsu.ui.element.programs.BachelorsFragment
import com.ibsu.ibsu.ui.element.programs.DoctorateFragment
import com.ibsu.ibsu.ui.element.programs.MastersFragment


class ViewPagerForProgramsAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    private val fragments: MutableList<Fragment> = mutableListOf()

    init {
        // Preload the fragments for all positions
        fragments.add(BachelorsFragment()) // Position 0
        fragments.add(MastersFragment()) // Position 1
        fragments.add(DoctorateFragment()) // Position 2
    }


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}