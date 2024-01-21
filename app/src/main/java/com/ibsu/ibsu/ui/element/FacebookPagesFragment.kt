package com.ibsu.ibsu.ui.element

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.databinding.FragmentFacebookPagesBinding
import com.ibsu.ibsu.ui.adapter.FanPagesAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.FBFanPagesViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FacebookPagesFragment :
    BaseFragment<FragmentFacebookPagesBinding>(FragmentFacebookPagesBinding::inflate) {
    private val viewModel: FBFanPagesViewModel by viewModels()
    private lateinit var fanPagesAdapter: FanPagesAdapter
    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeFanPages()
    }

    private fun observeFanPages() {
        viewModel.getFBFanPages()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myState.collect {
                    when (it) {
                        is ResponseState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is ResponseState.Error -> {
                            binding.errorMessage.text = it.message.toString()
                            binding.progressBar.visibility = View.GONE
                        }

                        is ResponseState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.headerTV.visibility = View.VISIBLE
                            binding.textViewAlert.visibility = View.VISIBLE
                            fanPagesAdapter.submitList(it.items)

                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        fanPagesAdapter = FanPagesAdapter()
        val recycler = binding.fanPagesRV
        val layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Set the span size for each item based on its position
                return 1
            }
        }
        recycler.adapter = fanPagesAdapter
        recycler.layoutManager = layoutManager
    }

}