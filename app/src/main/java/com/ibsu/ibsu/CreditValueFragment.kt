package com.ibsu.ibsu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ibsu.ibsu.databinding.FragmentCreditValueBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.ui.adapter.GamesAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.BachelorCreditValueViewModel
import com.ibsu.ibsu.ui.viewmodel.GameViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CreditValueFragment(private val programVal: String, private val type: String) : BaseFragment<FragmentCreditValueBinding>(FragmentCreditValueBinding::inflate) {
    private val viewModel: BachelorCreditValueViewModel by viewModels()
    override fun setup() {
        when(type){
            "Bachelor" -> {observeBachelorCreditValue()}
            "Master" -> {}
            "Doctorate" -> {}
        }
    }

    private fun observeBachelorCreditValue() {
        viewModel.getBachelorCreditValue(programVal)
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
                            if(requireContext().getCurrentLocale(requireContext()).language=="ka")
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