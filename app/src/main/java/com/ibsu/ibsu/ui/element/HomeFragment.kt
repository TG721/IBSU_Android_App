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
                val action =
                    HomeFragmentDirections.actionHomeFragmentToUsefulDocsFragment()
                findNavController().navigate(action)
            }
        }
    }




}