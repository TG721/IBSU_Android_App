package com.ibsu.ibsu.ui.element.contact

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentContactInfoBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.ui.adapter.ContactInfoItemAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.ContactViewModel
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ContactInfoFragment :
    BaseFragment<FragmentContactInfoBinding>(FragmentContactInfoBinding::inflate) {
    private val viewModel: ContactViewModel by viewModels()
    private lateinit var rvAdapter: ContactInfoItemAdapter
    override fun setup() {
        binding.addressTV.paintFlags = binding.addressTV.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        setupRecycler()
    }

    override fun observers() {
        observeItems()
        observeAddress()
    }

    private fun observeAddress() {
        viewModel.getAddress()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myState2.collect {
                    when (it) {
                        is ResponseState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is ResponseState.Error -> {
                            binding.errorMessage.text = it.message.toString()
                            binding.progressBar.visibility = View.GONE
                        }

                        is ResponseState.Success -> {
                            binding.addressTV.visibility = View.VISIBLE
                            if(requireContext().getCurrentLocale(requireContext()).language==georgianLocale)
                            binding.addressTV.text = it.items.addressGe
                            else binding.addressTV.text = it.items.addressEn

                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun observeItems() {
        viewModel.getContactInfo();
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myState.collect {
                    when (it) {
                        is ResponseState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is ResponseState.Error -> {
                            binding.errorMessage.text = it.message.toString()
                            Toast.makeText(requireContext(), getString(R.string.check_internet_connection_and_scroll_to_refresh), Toast.LENGTH_LONG).show()

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

    override fun listeners() {
        binding.addressTV.setOnClickListener {
            openIBSULocation()
        }

        val swipeLayout = binding.swipeRefreshLayout
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.ibsu))
        swipeLayout.setOnRefreshListener {
            observeAddress()
            observeItems()
            swipeLayout.isRefreshing = false

        }
    }

    fun openIBSULocation() {
        val latitude = 41.7973798
        val longitude = 44.77139
        val locationName = "IBSU" // Replace with the location name

        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$locationName")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

        // Specify the package for Google Maps, so it opens the app directly if installed
        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(requireContext().packageManager) != null) {
            // Google Maps app is installed, open the location in the app
            startActivity(mapIntent)
        } else {
            // Google Maps app is not installed, open in a web browser
            val mapUrl = "https://www.google.com/maps?q=$latitude,$longitude"
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))

            if (webIntent.resolveActivity(requireContext().packageManager) != null) {
                // Web browser is available, open the location in the browser
                startActivity(webIntent)
            } else {
                // Handle the case where neither the app nor the browser is available
                Toast.makeText(requireContext(), "No app to handle this action", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setupRecycler() {
        rvAdapter = ContactInfoItemAdapter(requireContext())
        val recycler = binding.rv
        val layoutManager = LinearLayoutManager(context)
        recycler.adapter = rvAdapter
        recycler.layoutManager = layoutManager
    }
}