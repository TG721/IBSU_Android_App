package com.ibsu.ibsu.ui.element.student_life

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentClubsBinding
import com.ibsu.ibsu.ui.adapter.ClubsAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.student_life.ClubViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClubsFragment : BaseFragment<FragmentClubsBinding>(FragmentClubsBinding::inflate) {
    private val viewModel: ClubViewModel by viewModels()
    private lateinit var clubsAdapter: ClubsAdapter
    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeClubs()
    }

    private fun observeClubs() {
        viewModel.getClubs();
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
                            clubsAdapter.submitList(it.items)

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
            observeClubs()
            swipeLayout.isRefreshing = false

        }
    }
    private fun setupRecycler() {
        clubsAdapter = ClubsAdapter(requireContext())
        val recycler = binding.clubsRV
        val layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Set the span size for each item based on its position
                return 1
            }
        }
        recycler.adapter = clubsAdapter
        recycler.layoutManager = layoutManager
    }

}