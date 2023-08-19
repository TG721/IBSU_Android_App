package com.ibsu.ibsu.ui.element

import android.util.Log
import android.util.Log.d
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.databinding.FragmentAdminStaffBinding
import com.ibsu.ibsu.ui.adapter.AdminStaffItemAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.AdminStaffViewModel
import com.ibsu.ibsu.utils.ResponseState
import com.ibsu.ibsu.utils.Schools
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminStaffFragment(val school: String) :
    BaseFragment<FragmentAdminStaffBinding>(FragmentAdminStaffBinding::inflate) {
    private val viewModel: AdminStaffViewModel by viewModels()
    private lateinit var adminStaffItemAdapter: AdminStaffItemAdapter
    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeItems()
    }

    private fun observeItems() {
        viewModel.getAdminStaff(school)

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

    private fun setupRecycler() {
        adminStaffItemAdapter = AdminStaffItemAdapter(requireContext())
        val recycler = binding.itemsRV
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adminStaffItemAdapter
        recycler.layoutManager = layoutManager
    }
}