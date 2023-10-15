package com.ibsu.ibsu.ui.element.programs

import android.util.Log.d
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.databinding.FragmentProgramAdminBinding
import com.ibsu.ibsu.ui.adapter.ProgramAdminAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.ProgramAdministrationViewModel
import com.ibsu.ibsu.ui.viewmodel.ProgramViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProgramAdminFragment :
    BaseFragment<FragmentProgramAdminBinding>(FragmentProgramAdminBinding::inflate) {
    private val viewModel: ProgramAdministrationViewModel by viewModels()
    private lateinit var rvAdapter: ProgramAdminAdapter
    private val sharedViewModel: ProgramViewModel by activityViewModels()

    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeItems()
    }

    private fun observeItems() {
        viewModel.getProgramAdministration(sharedViewModel.getTypeValue(), sharedViewModel.getProgramValue())
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
                            rvAdapter.submitList(it.items)

                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        rvAdapter = ProgramAdminAdapter(requireContext())
        val recycler = binding.rv
        val layoutManager = LinearLayoutManager(context)

        recycler.adapter = rvAdapter
        recycler.layoutManager = layoutManager
    }

}