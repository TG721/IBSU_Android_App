package com.ibsu.ibsu.ui.element

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.data.remote.model.UsefulDocsItem
import com.ibsu.ibsu.databinding.FragmentUsefulDocsBinding
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.adapter.UsefulDocsAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.UsefulDocsViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UsefulDocsFragment : BaseFragment<FragmentUsefulDocsBinding>(FragmentUsefulDocsBinding::inflate) {
        private val viewModel: UsefulDocsViewModel by viewModels()
        private lateinit var rvAdapter: UsefulDocsAdapter
        private lateinit var list: ArrayList<UsefulDocsItem>

        override fun setup() {
            setActionBarName(getString(R.string.useful_documents))
            showBackButton()
            hideBottomNavigation()
            setupRecycler()
        }

        private fun setupRecycler() {
            rvAdapter = UsefulDocsAdapter(requireContext())
            val recycler = binding.rv
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recycler.adapter = rvAdapter
            recycler.layoutManager = layoutManager
        }

        override fun observers() {
            observeItems()
        }

        private fun observeItems() {
            viewModel.getUsefulDoc()

            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.myState.collect {
                        when (it) {
                            is ResponseState.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }

                            is ResponseState.Error -> {
                                binding.errorMessage.text = it.message.toString()
                                binding.errorMessage.visibility = View.VISIBLE
                                binding.progressBar.visibility = View.GONE
                            }

                            is ResponseState.Success -> {
                                binding.progressBar.visibility = View.GONE
                                binding.errorMessage.visibility = View.GONE
//                            Log.d("Received list ", it.items.toString())
                                rvAdapter.submitList(it.items)
                                list = it.items

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

    override fun onResume() {
        super.onResume()
        setActionBarName(getString(R.string.useful_documents))

    }

    }