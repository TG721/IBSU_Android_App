package com.ibsu.ibsu.ui.element

import android.graphics.Color
import android.util.Log.d
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
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
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.HomeViewModel
import com.ibsu.ibsu.utils.ResponseState
import com.ibsu.ibsu.utils.WeekValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var listOfPictureURLs: List<String>
    private var isFirstResume = true
    override fun setup() {
        setupSlider()
        setupActionBar()
        showBottomMenu()
    }

    private fun showBottomMenu() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun setupActionBar() {
        val name = getString(R.string.programs)
        if (WeekValue.weekValue != null) {
            (activity as AppCompatActivity).supportActionBar?.title = HtmlCompat.fromHtml(
                "<font color='#FFFFFF'>IBSU - ${WeekValue.weekValue}</font>",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
        val activity = requireActivity()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    override fun listeners() {
        binding.apply {
            buttonIRO.setOnClickListener {

            }
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
                            d("beforeresultlist", "beforeeeeeee")
                            listOfPictureURLs = it.items.map { sliderEventItem -> sliderEventItem.pictureURL.toString() }
                            d("resultlist", listOfPictureURLs.toString())
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