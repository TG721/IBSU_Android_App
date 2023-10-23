package com.ibsu.ibsu.ui.element.student_life

import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.ibsu.ibsu.databinding.FragmentNewsImageFullScreenBinding
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.adapter.NewsImagesViewPagerAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsImageFullScreenFragment : BaseFragment<FragmentNewsImageFullScreenBinding>(
    FragmentNewsImageFullScreenBinding::inflate
) {
    private val args by navArgs<NewsImageFullScreenFragmentArgs>()
    override fun setup() {

        val viewPager = binding.viewPagerForSportNews

        val adapter = NewsImagesViewPagerAdapter(args.listOfImages.toList(), requireActivity())
        viewPager.adapter = adapter

        viewPager.doOnPreDraw {
            viewPager.currentItem = args.currentImagePos
        }


        binding.pageNumberTextView.text = "${args.currentImagePos + 1}/${args.listOfImages.size}"

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.pageNumberTextView.text = "${position + 1}/${args.listOfImages.size}"
            }
        })


    }

    override fun onResume() {
        super.onResume()
        hideBottomNavigation()
        showBackButton()
    }

}