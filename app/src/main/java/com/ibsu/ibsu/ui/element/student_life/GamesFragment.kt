package com.ibsu.ibsu.ui.element.student_life

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentGamesBinding
import com.ibsu.ibsu.ui.adapter.GamesAdapter
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.ui.viewmodel.student_life.GameViewModel
import com.ibsu.ibsu.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
//GameTypesFragment
class GamesFragment : BaseFragment<FragmentGamesBinding>(FragmentGamesBinding::inflate) {
    private val viewModel: GameViewModel by viewModels()
    private lateinit var gamesAdapter: GamesAdapter
    override fun setup() {
        setupRecycler()
    }

    override fun observers() {
        observeGames()
        observeGameRoom()
    }

    private fun observeGameRoom() {
        viewModel.getGameRoomLocation()
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
                            binding.progressBar.visibility = View.GONE
                            binding.gameRoomTV.text =
                                "${getString(R.string.game_room)}${it.items.location}"

                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun observeGames() {
        viewModel.getGames()
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
                            gamesAdapter.submitList(it.items)

                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        gamesAdapter = GamesAdapter()
        val recycler = binding.gamesRV
        val layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                // Set the span size for each item based on its position
                return 1
            }
        }
        recycler.adapter = gamesAdapter
        recycler.layoutManager = layoutManager
    }


}