package com.ibsu.ibsu.ui.element.contact

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.domain.model.WorkingHoursItem
import com.ibsu.ibsu.databinding.FragmentWorkingHoursBinding
import com.ibsu.ibsu.ui.adapter.WorkingHoursAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.contact.WorkingHoursViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkingHoursFragment :
    BaseFragment<FragmentWorkingHoursBinding>(FragmentWorkingHoursBinding::inflate) {
    private val viewModel: WorkingHoursViewModel by viewModels()
    private lateinit var rvAdapter: WorkingHoursAdapter
    private var list = mutableListOf<com.ibsu.ibsu.domain.model.WorkingHoursItem>()

    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeItems()
    }


    @SuppressLint("SuspiciousIndentation")
    private fun observeItems() {
        viewModel.getWorkingHours();
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
                            rvAdapter.submitList(it.items)

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
        rvAdapter = WorkingHoursAdapter()
        val recycler = binding.recyclerView
        val layoutManager = LinearLayoutManager(context)

        recycler.adapter = rvAdapter
        recycler.layoutManager = layoutManager
    }


}