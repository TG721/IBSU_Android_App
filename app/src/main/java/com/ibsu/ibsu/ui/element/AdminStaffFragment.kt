package com.ibsu.ibsu.ui.element

import android.util.Log
import android.util.Log.d
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentAdminStaffBinding
import com.ibsu.ibsu.ui.adapter.AdminStaffItemAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.AdminStaffViewModel
import com.ibsu.ibsu.ui.viewmodel.SchoolViewModel
import com.ibsu.ibsu.utils.ResponseState
import com.ibsu.ibsu.utils.Schools
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminStaffFragment() :
    BaseFragment<FragmentAdminStaffBinding>(FragmentAdminStaffBinding::inflate) {
    private val viewModel: AdminStaffViewModel by viewModels()
    private lateinit var adminStaffItemAdapter: AdminStaffItemAdapter
    private val sharedViewModel: SchoolViewModel by activityViewModels()

    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeItems()
    }

    private fun observeItems() {
        viewModel.getAdminStaff(sharedViewModel.getSchoolValue())

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
                            d("errrorrrr", it.message.toString())
                        }

                        is ResponseState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Log.d("Received list ", it.items.toString())
                            adminStaffItemAdapter.submitList(it.items)

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
        adminStaffItemAdapter = AdminStaffItemAdapter(requireContext(), sharedViewModel.getEmailVisibility())
        val recycler = binding.itemsRV
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adminStaffItemAdapter
        recycler.layoutManager = layoutManager
    }
}