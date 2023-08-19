package com.ibsu.ibsu.ui.element

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.data.remote.model.SelfGovernanceItem
import com.ibsu.ibsu.databinding.FragmentSelfGovernanceBinding
import com.ibsu.ibsu.ui.adapter.SelfGovernanceAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.SelfGovernanceViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelfGovernanceFragment :
    BaseFragment<FragmentSelfGovernanceBinding>(FragmentSelfGovernanceBinding::inflate) {
    private val viewModel: SelfGovernanceViewModel by viewModels()
    private lateinit var selfGovernanceAdapter: SelfGovernanceAdapter
    private var selfGovernanceList = mutableListOf<SelfGovernanceItem>()
    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeItems()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun observeItems() {
        viewModel.getSelfGovernance();
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
                            for (i in 0 until it.items.size) {
                                selfGovernanceList.add(it.items.elementAt(i))

                            }

                            selfGovernanceAdapter.submitList(selfGovernanceList)

                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        selfGovernanceAdapter = SelfGovernanceAdapter(requireContext())
        val recycler = binding.selfGovernanceRV
        var spanCount = 2
//        if(requireContext().getCurrentLocale(requireContext()).language=="ka") spanCount = 1
        val layoutManager =
            GridLayoutManager(context, spanCount, LinearLayoutManager.VERTICAL, false)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Set the span size for each item based on its position
                return 1
            }
        }
        recycler.adapter = selfGovernanceAdapter
        recycler.layoutManager = layoutManager
    }

}