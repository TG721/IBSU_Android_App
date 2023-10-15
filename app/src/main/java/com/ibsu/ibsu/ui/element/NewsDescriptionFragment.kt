package com.ibsu.ibsu.ui.element

import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentNewsDescriptionBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.ui.adapter.NewsImagesAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDescriptionFragment : BaseFragment<FragmentNewsDescriptionBinding>(
    FragmentNewsDescriptionBinding::inflate
) {
    private lateinit var rvAdapter: NewsImagesAdapter
    private val args by navArgs<NewsDescriptionFragmentArgs>()

    override fun setup() {
        if (requireContext().getCurrentLocale(requireContext()).language == georgianLocale) {
            binding.descriptionTV.text = args.newsItems.descriptionGe
            binding.headlineTV.text = args.newsItems.headlineGe
            binding.dateTV.text = args.newsItems.dateGe
        } else {
            binding.descriptionTV.text = args.newsItems.descriptionEn
            binding.headlineTV.text = args.newsItems.headlineEn
            binding.dateTV.text = args.newsItems.dateEn
        }
        if (args.newsItems.pictureLinksList?.size == 0 || args.newsItems.pictureLinksList == null)
            binding.textViewImagesTitle.visibility = View.INVISIBLE
        setupRecycler()
    }


    private fun setupRecycler() {
        rvAdapter = NewsImagesAdapter()
        val recycler = binding.rv
        val layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        recycler.adapter = rvAdapter
        recycler.layoutManager = layoutManager

        rvAdapter.submitList(args.newsItems.pictureLinksList)
    }

    override fun onResume() {
        super.onResume()
        setActionBarName(getString(R.string.more_information))
    }


}