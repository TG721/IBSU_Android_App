package com.ibsu.ibsu.ui.element.programs

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ibsu.ibsu.databinding.FragmentCreditValueBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.programs.CreditValueViewModel
import com.ibsu.ibsu.ui.viewmodel.programs.ProgramViewModel
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CreditValueFragment() : BaseFragment<FragmentCreditValueBinding>(FragmentCreditValueBinding::inflate) {
    private val viewModel: CreditValueViewModel by viewModels()
    private val sharedViewModel: ProgramViewModel by activityViewModels()

    override fun setup() {
        observeCreditValue()
    }

    private fun observeCreditValue() {
        viewModel.getBachelorCreditValue(sharedViewModel.getTypeValue(), sharedViewModel.getProgramValue())
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
                            if(requireContext().getCurrentLocale(requireContext()).language==georgianLocale)
                            binding.result.text = it.items.valueGe
                            else binding.result.text = it.items.valueEn

                        }

                        else -> {}
                    }
                }
            }
        }
    }

}