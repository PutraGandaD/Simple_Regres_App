package com.putragandad.simpleregresapp.presentation.fragment.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.putragandad.simpleregresapp.domain.models.UserData
import com.putragandad.simpleregresapp.presentation.R
import com.putragandad.simpleregresapp.presentation.adapter.OnItemClickListener
import com.putragandad.simpleregresapp.presentation.adapter.UserListAdapter
import com.putragandad.simpleregresapp.presentation.databinding.FragmentThirdScreenBinding
import com.putragandad.simpleregresapp.presentation.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.navigation.koinNavGraphViewModel


class ThirdScreenFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel by koinNavGraphViewModel<SharedViewModel>(R.id.regresapp_navgraph)

    private val userListAdapter = UserListAdapter(this)

    private var TOTAL_PAGES = 0 // total pages from api
    private var TOTAL_ITEM_PER_PAGES = 0
    private var PAGE_COUNT_REQUEST = 1 // pages that we add to the api query

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val layoutManager = binding.rvUserData.layoutManager as LinearLayoutManager
        binding.rvUserData.setLayoutManager(layoutManager)
        binding.rvUserData.adapter = userListAdapter

        sharedViewModel.getListUser(PAGE_COUNT_REQUEST) // get data from api start with page 1

        observer()
        handleScrollPagination()
    }

    private fun observer() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.thirdScreenUiState.collectLatest { uiState ->
                    // update total pages
                    uiState.totalPages?.let {
                        TOTAL_PAGES = it
                    }

                    // update total item per pages
                    uiState.totalItemPerPages?.let {
                        TOTAL_ITEM_PER_PAGES = it
                    }

                    // submit/update data to recyclerview
                    if(uiState.userData!!.isNotEmpty()) {
                        addNewUsersDataToRV(uiState.userData)
                    }

                    // handle error message
                    uiState.message?.let {
                        Snackbar.make(requireView(), uiState.message, Snackbar.LENGTH_LONG)
                            .setAction("Try Again") {
                                sharedViewModel.getListUser(PAGE_COUNT_REQUEST)
                            }.show()
                        sharedViewModel.messageThirdScreenShown()
                    }

                    // handle loading state
                    if(uiState.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.progressBarItem.setVisibility(View.VISIBLE)
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.progressBarItem.visibility = View.GONE
                    }

                    // handle if data isn't loading + api call not success (means error), show empty data
                    if(uiState.isError && userListAdapter.currentList.isEmpty()) {
                        binding.tvDataEmpty.visibility = View.VISIBLE
                    } else {
                        binding.tvDataEmpty.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun handleScrollPagination() {
        binding.rvUserData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    val canScrollDownMore = recyclerView.canScrollVertically(1)
                    if (!canScrollDownMore) {
                        if(PAGE_COUNT_REQUEST < TOTAL_PAGES) {
                            PAGE_COUNT_REQUEST++
                            sharedViewModel.getListUser(PAGE_COUNT_REQUEST)
                        }
                    }
                }
            }
        })
    }

    private fun addNewUsersDataToRV(newUsers: List<UserData>) {
        val currentList = userListAdapter.currentList.toMutableList() // copy current data from rv
        currentList.addAll(newUsers) // add data
        userListAdapter.submitList(currentList) // submit latest data
    }

    override fun onUserClicked(userData: UserData) {
        sharedViewModel.setSelectedUserName("${userData.firstName} ${userData.lastName}")
        findNavController().popBackStack()
        Toast.makeText(requireActivity(), "User selected!", Toast.LENGTH_SHORT).show()
    }
}