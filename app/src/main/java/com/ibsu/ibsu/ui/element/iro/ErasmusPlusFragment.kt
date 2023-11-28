package com.ibsu.ibsu.ui.element.iro

import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentErasmusPlusBinding
import com.ibsu.ibsu.ui.adapter.ExchangeUniversityItemAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.iro.ErasmusPlusViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ErasmusPlusFragment : BaseFragment<FragmentErasmusPlusBinding>(FragmentErasmusPlusBinding::inflate) {
    private val viewModel: ErasmusPlusViewModel by viewModels()
    private lateinit var adapter: ExchangeUniversityItemAdapter
    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeItems()
    }

    private fun observeItems() {
        viewModel.getExchangeUniversitiesForErasmusPlus();
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
                            Toast.makeText(requireContext(), getString(R.string.check_internet_connection_and_scroll_to_refresh), Toast.LENGTH_LONG).show()

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

    override fun listeners() {
        val swipeLayout = binding.swipeRefreshLayout
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.ibsu))
        swipeLayout.setOnRefreshListener {
            observeItems()
            swipeLayout.isRefreshing = false

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