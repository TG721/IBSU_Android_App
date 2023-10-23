package com.ibsu.ibsu.ui.element.student_life

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.data.remote.model.NewsItem
import com.ibsu.ibsu.databinding.FragmentSportNewsBinding
import com.ibsu.ibsu.ui.adapter.NewsAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.SportNewsViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SportNewsFragment :
    BaseFragment<FragmentSportNewsBinding>(FragmentSportNewsBinding::inflate) {
    private val viewModel: SportNewsViewModel by viewModels()
    private lateinit var rvAdapter: NewsAdapter
    private var list = mutableListOf<NewsItem>()

    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeItems()
    }


    @SuppressLint("SuspiciousIndentation")
    private fun observeItems() {
        viewModel.getNews()
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
                                list.add(it.items.elementAt(i))

                            }

                            rvAdapter.submitList(list)

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
        rvAdapter = NewsAdapter(requireContext())
        val recycler = binding.rv
        val layoutManager = LinearLayoutManager(context)

        recycler.adapter = rvAdapter
        recycler.layoutManager = layoutManager
    }


}