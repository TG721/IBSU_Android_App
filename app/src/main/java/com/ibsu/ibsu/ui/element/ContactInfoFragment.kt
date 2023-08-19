package com.ibsu.ibsu.ui.element

import com.ibsu.ibsu.databinding.FragmentContactInfoBinding
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactInfoFragment :
    BaseFragment<FragmentContactInfoBinding>(FragmentContactInfoBinding::inflate) {
    override fun setup() {
    }

}