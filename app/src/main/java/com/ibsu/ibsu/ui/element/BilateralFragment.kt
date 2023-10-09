package com.ibsu.ibsu.ui.element

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentBilateralBinding
import com.ibsu.ibsu.databinding.FragmentErasmusPlusBinding
import com.ibsu.ibsu.ui.adapter.ExchangeUniversityItemAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.BilateralViewModel
import com.ibsu.ibsu.ui.viewmodel.ErasmusPlusViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BilateralFragment : BaseFragment<FragmentBilateralBinding>(FragmentBilateralBinding::inflate) {
    private val viewModel: BilateralViewModel by viewModels()
    private lateinit var adapter: ExchangeUniversityItemAdapter
    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeItems()
    }

    private fun observeItems() {
        viewModel.getExchangeUniversitiesForBilateral();
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
                            adapter.submitList(it.items)

                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        adapter = ExchangeUniversityItemAdapter(requireContext())
        val recycler = binding.clubsRV
        val layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
    }
}