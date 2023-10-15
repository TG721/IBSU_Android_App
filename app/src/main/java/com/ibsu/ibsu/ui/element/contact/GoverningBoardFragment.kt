package com.ibsu.ibsu.ui.element.contact

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.data.remote.model.GovernanceItem
import com.ibsu.ibsu.databinding.FragmentGoverningBoardBinding
import com.ibsu.ibsu.ui.adapter.GovernanceAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.GoverningBoardViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class GoverningBoardFragment :
    BaseFragment<FragmentGoverningBoardBinding>(FragmentGoverningBoardBinding::inflate) {
    private val viewModel: GoverningBoardViewModel by viewModels()
    private lateinit var governanceAdapter: GovernanceAdapter
    private var governanceList = mutableListOf<GovernanceItem>()
    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeItems()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun observeItems() {
        viewModel.getGoverningBoard();
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
                            governanceAdapter.submitList(it.items)

                        }

                        else -> {}
                    }
                }
            }
        }
    }

    override fun listeners() {
        val swipeLayout = binding.swipeRefreshLayout
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.ibsu))
        swipeLayout.setOnRefreshListener {
            observeItems()
            swipeLayout.isRefreshing = false

        }
    }

    private fun setupRecycler() {
        governanceAdapter = GovernanceAdapter(requireContext())
        val recycler = binding.governanceRV
        var spanCount = 2

        val layoutManager =
            GridLayoutManager(context, spanCount, LinearLayoutManager.VERTICAL, false)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Set the span size for each item based on its position
                return 1
            }
        }
        recycler.adapter = governanceAdapter
        recycler.layoutManager = layoutManager
    }

}