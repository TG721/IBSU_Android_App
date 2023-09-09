package com.ibsu.ibsu.ui.element

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.data.remote.model.LecturersItem
import com.ibsu.ibsu.databinding.FragmentLectrurersBinding
import com.ibsu.ibsu.ui.adapter.LecturersAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.LecturersViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LecturersFragment(val school: String, private val emailVisibility: Boolean = false) :
    BaseFragment<FragmentLectrurersBinding>(FragmentLectrurersBinding::inflate) {
    private val viewModel: LecturersViewModel by viewModels()
    private lateinit var rvAdapter: LecturersAdapter
    private lateinit var lecturers: ArrayList<LecturersItem>

    override fun setup() {
        setupRecycler()
    }

    private fun setupRecycler() {
        rvAdapter = LecturersAdapter(requireContext(), emailVisibility)
        val recycler = binding.itemsRV
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.adapter = rvAdapter
        recycler.layoutManager = layoutManager
    }

    override fun observers() {
        observeItems()
    }

    private fun observeItems() {
        viewModel.getLecturers(school)

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
//                            Log.d("errrorrrr", it.message.toString())
                        }

                        is ResponseState.Success -> {
                            binding.progressBar.visibility = View.GONE
//                            Log.d("Received list ", it.items.toString())
                            binding.searchView.visibility = View.VISIBLE
                            rvAdapter.submitList(it.items)
                            lecturers = it.items

                        }

                        else -> {}
                    }
                }
            }
        }
    }

    override fun listeners() {
        val searchView = binding?.searchView
        searchView?.clearFocus()
        searchView?.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (lecturers?.size != 0){
                val filteredList = ArrayList<LecturersItem>()
                for (item in lecturers)
                    if (item.nameEn.lowercase().contains(newText.toString().lowercase()) || item.nameGe.contains(newText.toString().lowercase()))
                        filteredList.add(item)
                rvAdapter.submitList(filteredList)
            }
                return false
            }

        })
    }

}
