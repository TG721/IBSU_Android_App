package com.ibsu.ibsu

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.ibsu.ibsu.data.remote.model.NewsItem
import com.ibsu.ibsu.databinding.FragmentNewsDescriptionBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.ui.adapter.NewsAdapter
import com.ibsu.ibsu.ui.adapter.NewsImagesAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.element.EventDescriptionFragmentArgs
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsDescriptionFragment : BaseFragment<FragmentNewsDescriptionBinding>(
    FragmentNewsDescriptionBinding::inflate) {
    private lateinit var rvAdapter: NewsImagesAdapter
    private val args by navArgs<NewsDescriptionFragmentArgs>()

    override fun setup() {
        if(requireContext().getCurrentLocale(requireContext()).language=="ka") {
            binding.descriptionTV.text = args.newsItems.descriptionGe
            binding.headlineTV.text = args.newsItems.headlineGe
            binding.dateTV.text = args.newsItems.dateGe
        }
        else {
            binding.descriptionTV.text = args.newsItems.descriptionEn
            binding.headlineTV.text = args.newsItems.headlineEn
            binding.dateTV.text = args.newsItems.dateEn
        }
        setupRecycler()
    }


    private fun setupRecycler() {
        rvAdapter = NewsImagesAdapter()
        val recycler = binding.rv
        val layoutManager = GridLayoutManager(context,  2, GridLayoutManager.VERTICAL, false)

        recycler.adapter = rvAdapter
        recycler.layoutManager = layoutManager

        rvAdapter.submitList(args.newsItems.pictureLinksList)
    }

    override fun onResume() {
        super.onResume()
        setActionBarName(getString(R.string.more_information))
    }



}