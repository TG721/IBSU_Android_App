package com.ibsu.ibsu.ui.element.programs

import android.graphics.drawable.ColorDrawable
import android.util.Log.d
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentBachelorsBinding
import com.ibsu.ibsu.domain.model.Programs
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.ui.adapter.ProgramAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.SchoolViewModel
import com.ibsu.ibsu.ui.viewmodel.programs.BachelorViewModel
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale
import com.ibsu.ibsu.utils.ProgramLanguageFilter
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BachelorsFragment() :
    BaseFragment<FragmentBachelorsBinding>(FragmentBachelorsBinding::inflate) {
    private val viewModel: BachelorViewModel by viewModels()
    private lateinit var programAdapter: ProgramAdapter
    private var programUIList = mutableListOf<com.ibsu.ibsu.domain.model.ProgramItem>()
    private val sharedViewModel: SchoolViewModel by activityViewModels()

    override fun setup() {
        setupRecycler()
        setupDropDownMenus()
    }

    private fun setupDropDownMenus() {
        val sortingMethods: Array<String> = resources.getStringArray(R.array.filter_programs)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, sortingMethods)
        binding.autoCompleteTextViewSector.setAdapter(arrayAdapter)
        binding.autoCompleteTextViewSector.showSoftInputOnFocus = false
        binding.autoCompleteTextViewSector.setDropDownBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(requireContext(), R.color.anti_line))
        )
    }

    override fun observers() {
        observePrograms()
    }

    override fun listeners() {
        binding.autoCompleteTextViewSector.setOnItemClickListener { adapterView, _, i, _ ->
            when {
                (adapterView.getItemAtPosition(i)).toString() == getString(R.string.english_programs) -> {
                    val englishPrograms = viewModel.filterProgramsByLanguage(programUIList, ProgramLanguageFilter.ENGLISH)
                    viewModel.setSelectedFilterState(ProgramLanguageFilter.ENGLISH)
                    programAdapter.submitList(englishPrograms)
                }


                (adapterView.getItemAtPosition(i)).toString() == getString(R.string.georgian_programs) -> {
                    val georgianPrograms = viewModel.filterProgramsByLanguage(programUIList, ProgramLanguageFilter.GEORGIAN)
                    viewModel.setSelectedFilterState(ProgramLanguageFilter.GEORGIAN)
                    programAdapter.submitList(georgianPrograms)
                }

                (adapterView.getItemAtPosition(i)).toString() == getString(R.string.all_programs) -> {
                    viewModel.setSelectedFilterState(ProgramLanguageFilter.NEITHER)
                    programAdapter.submitList(programUIList)
                }

                else -> {}
            }
        }


    }


    private fun observePrograms() {
        viewModel.getBachelorPrograms();
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
//
                        }

                        is ResponseState.Success -> {
                            binding.progressBar.visibility = View.GONE
//                            programUIList = mutableListOf<ProgramItem>()
                            programUIList.clear()
                            for (i in 0 until it.items.size) {
                                if (sharedViewModel.getSchoolValue() != "") {
                                    if (it.items.elementAt(i).School == sharedViewModel.getSchoolValue())
                                        programUIList.add(it.items.elementAt(i))
                                } else programUIList.add(it.items.elementAt(i))

                            }

                            when(viewModel.getSelectedFilterState()){
                                ProgramLanguageFilter.ENGLISH -> {programAdapter.submitList(viewModel.filterProgramsByLanguage(programUIList, ProgramLanguageFilter.ENGLISH))}
                                ProgramLanguageFilter.GEORGIAN -> {programAdapter.submitList(viewModel.filterProgramsByLanguage(programUIList, ProgramLanguageFilter.GEORGIAN))}
                                ProgramLanguageFilter.NEITHER-> { programAdapter.submitList(programUIList)}
                            }


                        }

                        else -> {}
                    }
                }
            }
        }
    }


    private fun setupRecycler() {
        programAdapter = ProgramAdapter("bachelor")
        val recycler = binding.programRV
        var spanCount = 2
        if (requireContext().getCurrentLocale(requireContext()).language == georgianLocale) spanCount = 1
        val layoutManager =
            GridLayoutManager(context, spanCount, LinearLayoutManager.VERTICAL, false)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Set the span size for each item based on its position
                return 1
            }
        }
        recycler.adapter = programAdapter
        recycler.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()
        setupDropDownMenus()
        d("teng", "onResume called")

    }

    override fun onStart() {
        super.onStart()
        d("teng","onStart called")
    }

    override fun onStop() {
        super.onStop()
        d("teng","onStop called")
    }

    override fun onPause() {
        super.onPause()
        d("teng","onPause called")
    }
}

