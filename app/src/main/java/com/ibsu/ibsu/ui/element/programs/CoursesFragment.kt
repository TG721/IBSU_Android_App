package com.ibsu.ibsu.ui.element.programs

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.domain.model.CoursesItem
import com.ibsu.ibsu.databinding.FragmentCoursesBinding
import com.ibsu.ibsu.ui.adapter.CourseItemAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.programs.CoursesViewModel
import com.ibsu.ibsu.ui.viewmodel.programs.ProgramViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoursesFragment :
    BaseFragment<FragmentCoursesBinding>(FragmentCoursesBinding::inflate) {
    private val viewModel: CoursesViewModel by viewModels()
    private val sharedViewModel: ProgramViewModel by activityViewModels()

    private lateinit var rvAdapter: CourseItemAdapter
    private var list = mutableListOf<com.ibsu.ibsu.domain.model.CoursesItem>()
    private var etcFilterSelected = "Any"
    override fun setup() {
        setupRecycler()
        setupDropDownMenus()
    }

    override fun observers() {
            observeItems()

    }

    private fun setupDropDownMenus() {
        val sortingMethods: Array<String> = resources.getStringArray(R.array.filter_by_credit)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, sortingMethods)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView.showSoftInputOnFocus = false
        binding.autoCompleteTextView.setDropDownBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(requireContext(), R.color.anti_line))
        )

    }


    @SuppressLint("SuspiciousIndentation")
    private fun observeItems() {
        viewModel.getBachelorCourses(sharedViewModel.getTypeValue(), sharedViewModel.getProgramValue())
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
                            list.clear()
                            for (i in 0 until it.items.size) {
                                list.add(it.items.elementAt(i))

                            }
                            rvAdapter.submitList(list)
                            binding.searchView.visibility = View.VISIBLE
                            binding.textInputLayout.visibility = View.VISIBLE
                            binding.checkBox.visibility = View.VISIBLE
//                            binding.filters.visibility = View.VISIBLE

                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        rvAdapter = CourseItemAdapter()
        val recycler = binding.rv
        val layoutManager = LinearLayoutManager(context)

        recycler.adapter = rvAdapter
        recycler.layoutManager = layoutManager
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
                if (list?.size != 0) {
                    if(newText?.length!! > 0 ) {
                        binding.autoCompleteTextView.setText(requireContext().getString(R.string.filter_by_credits))
                        binding.checkBox.isChecked = false
                        setupDropDownMenus()
                        binding.autoCompleteTextView.visibility = View.GONE
                        binding.textInputLayout.visibility = View.GONE
                        binding.checkBox.visibility = View.GONE
//                        binding.filters.visibility = View.GONE
                    } else {
                        binding.autoCompleteTextView.visibility = View.VISIBLE
                        binding.textInputLayout.visibility = View.VISIBLE
                        binding.checkBox.visibility = View.VISIBLE
//                        binding.filters.visibility = View.VISIBLE
                    }
                    val filteredList = ArrayList<com.ibsu.ibsu.domain.model.CoursesItem>()
                    for (item in list)
                        if (item.nameEn.lowercase()
                                .contains(newText.toString().lowercase()) || item.nameGe.contains(
                                newText.toString().lowercase()
                            )
                        )
                            filteredList.add(item)
                    rvAdapter.submitList(filteredList)
                }
                return false
            }

        })

        binding.autoCompleteTextView.setOnItemClickListener { adapterView, _, i, _ ->
            searchView?.clearFocus()
            when {
                (adapterView.getItemAtPosition(i)).toString() == "ნებისმიერი" -> {
                    if (binding.checkBox.isChecked) rvAdapter.submitList(list.filter { it2 -> it2.prerequisites!!.isEmpty() })
                    else rvAdapter.submitList(list)
                }

                (adapterView.getItemAtPosition(i)).toString() == "Any" -> {
                    if (binding.checkBox.isChecked) rvAdapter.submitList(list.filter { it -> it.prerequisites!!.isEmpty() })
                    else rvAdapter.submitList(list)
                }

                (adapterView.getItemAtPosition(i)).toString().isDigitsOnly() -> {
                    if (binding.checkBox.isChecked) rvAdapter.submitList(list.filter { it -> it.prerequisites!!.isEmpty() && it.ETC == adapterView.getItemAtPosition(i).toString().toInt() })
                    else {
                        val filteredEcts = list.filter {
                            it.ETC == adapterView.getItemAtPosition(i).toString().toInt()
                        }
                        rvAdapter.submitList(filteredEcts)
                    }
                }


                else -> {}
            }
            etcFilterSelected = adapterView.getItemAtPosition(i).toString()
        }
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                searchView?.clearFocus()
                when(etcFilterSelected){
                    "Any", "Credits", "ნებისმიერი", "კრედიტები" -> {
                        val filteredNopre = list.filter {
                            it.prerequisites!!.isEmpty()
                        }
                        rvAdapter.submitList(filteredNopre)
                    }
                    "3" -> {
                        val filteredNopre = list.filter {
                            it.prerequisites!!.isEmpty() && it.ETC == 3
                        }
                        rvAdapter.submitList(filteredNopre)
                    }
                    "4" -> {
                        val filteredNopre = list.filter {
                            it.prerequisites!!.isEmpty() && it.ETC == 4
                        }
                        rvAdapter.submitList(filteredNopre)
                    }
                    "5" -> {
                        val filteredNopre = list.filter {
                            it.prerequisites!!.isEmpty() && it.ETC == 5
                        }
                        rvAdapter.submitList(filteredNopre)
                    }
                    "6" -> {
                        val filteredNopre = list.filter {
                            it.prerequisites!!.isEmpty() && it.ETC == 6
                        }
                        rvAdapter.submitList(filteredNopre)
                    }
                    "7" -> {
                        val filteredNopre = list.filter {
                            it.prerequisites!!.isEmpty() && it.ETC == 7
                        }
                        rvAdapter.submitList(filteredNopre)
                    }
                }

            } else {
                when(etcFilterSelected){
                    "Any", "Credits", "ნებისმიერი", "კრედიტები"  -> {
                        rvAdapter.submitList(list)
                    }
                    "3" -> {
                        val filteredNopre = list.filter {
                            it.ETC == 3
                        }
                        rvAdapter.submitList(filteredNopre)
                    }
                    "4" -> {
                        val filteredNopre = list.filter {
                            it.ETC == 4
                        }
                        rvAdapter.submitList(filteredNopre)
                    }
                    "5" -> {
                        val filteredNopre = list.filter {
                            it.ETC == 5
                        }
                        rvAdapter.submitList(filteredNopre)
                    }
                    "6" -> {
                        val filteredNopre = list.filter {
                            it.ETC == 6
                        }
                        rvAdapter.submitList(filteredNopre)
                    }
                    "7" -> {
                        val filteredNopre = list.filter {
                            it.prerequisites!!.isEmpty() && it.ETC == 7
                        }
                        rvAdapter.submitList(filteredNopre)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupDropDownMenus()
    }

}