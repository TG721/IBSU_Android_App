package com.ibsu.ibsu.ui.element

import android.graphics.Color
import android.util.Log.d
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentHomeBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.extensions.hideBackButton
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.HomeViewModel
import com.ibsu.ibsu.ui.viewmodel.SchoolViewModel
import com.ibsu.ibsu.ui.viewmodel.WeekValueViewModel
import com.ibsu.ibsu.utils.LanguagesLocale
import com.ibsu.ibsu.utils.ResponseState
import com.ibsu.ibsu.utils.WeekValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private val weekValueViewModel: WeekValueViewModel by viewModels()
    private val sharedViewModel: SchoolViewModel by activityViewModels()

    private lateinit var listOfPictureURLs: List<String>
    private var isFirstResume = true
    override fun setup() {
        sharedViewModel.setSchoolValue("")
        setupSlider()
        showBottomMenu()
        observeWeekValue();
        hideBackButton()
    }

    private fun observeWeekValue() {
        weekValueViewModel.getCurrentWeek();
        var weekValue: String? = null
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                weekValueViewModel.myState.collect {
                    when (it) {
                        is ResponseState.Loading -> {
                            setActionBarName(getString(R.string.loading))
                        }

                        is ResponseState.Error -> {
                            weekValue = getString(R.string.no_week_value_found)
                            WeekValue.weekValue = weekValue
                            Toast.makeText(requireContext(), getString(R.string.check_internet_connection_and_scroll_to_refresh), Toast.LENGTH_LONG).show()
                            setActionBarName(weekValue!!)
                        }

                        is ResponseState.Success -> {
                            weekValue = if(requireContext().getCurrentLocale().language== LanguagesLocale.georgianLocale)
                                it.items.weekValueGe
                            else it.items.weekValueEn

                            WeekValue.weekValue = weekValue
                            setActionBarName("IBSU - $weekValue")
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun showBottomMenu() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
    }



    override fun listeners() {
        binding.apply {
            buttonPrograms.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToProgramsFragment(null)
                findNavController().navigate(action)
            }
            buttonStudentLife.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToEntertainmentFragment()
                findNavController().navigate(action)
            }
            buttonSchool.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToSchoolSelectionFragment()
                findNavController().navigate(action)
            }
            buttonContact.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToContactFragment()
                findNavController().navigate(action)
            }
            buttonUsefulDocuments.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToUsefulDocsFragment()
                findNavController().navigate(action)
            }
            buttonIRO.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToExchangeProgramsFragment()
                findNavController().navigate(action)
            }

            val swipeLayout = swipeRefreshLayout
            swipeLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.ibsu))
            swipeLayout.setOnRefreshListener {
                observeWeekValue()
                setupSlider()
                swipeLayout.isRefreshing = false

            }
        }
    }

    private fun setupSlider() {
        //observing slider events
        viewModel.getSliderEvents()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myState.collect {
                    when (it) {
                        is ResponseState.Loading -> {

                        }

                        is ResponseState.Error -> {
                            d("error", "error is this " + it.message.toString())
                        }

                        is ResponseState.Success -> {
                            binding.swipeRefreshLayout.setEnabled(false);
                            binding.imageSlider.visibility = View.VISIBLE
                            listOfPictureURLs = it.items.map { sliderEventItem -> sliderEventItem.pictureURL.toString() }
                            insertSlideData(listOfPictureURLs)
                            //setup click listener
                            binding.imageSlider.setItemClickListener(object : ItemClickListener {
                                override fun doubleClick(position: Int) {

                                }

                                override fun onItemSelected(position: Int) {
                                    val clickedEvent = it.items[position]
                                    val action =
                                        HomeFragmentDirections.actionHomeFragmentToEventDescriptionFragment(
                                            clickedEventInfo = clickedEvent
                                        )
                                    findNavController().navigate(action)
                                }
                            })
                        }

                        else -> {}
                    }
                }
            }
        }
        //nothing
    }

    private fun insertSlideData(list: List<String>) {
        val imageSlider = binding.imageSlider
        val slideModels = list.map { SlideModel(it, null, null) }
        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP)
    }

    override fun onResume() {
        super.onResume()
//        if (isFirstResume) {
//            isFirstResume = false
//        } else {
//            insertSlideData(listOfPictureURLs)
//            binding.imageSlider.visibility = View.VISIBLE
//        }

    }


}