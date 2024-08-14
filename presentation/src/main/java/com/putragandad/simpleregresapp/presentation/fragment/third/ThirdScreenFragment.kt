package com.putragandad.simpleregresapp.presentation.fragment.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
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
    private var CURRENT_PAGES = 0
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

        // rv adapter setup
        val layoutManager = binding.rvUserData.layoutManager as LinearLayoutManager
        binding.rvUserData.setLayoutManager(layoutManager)
        binding.rvUserData.adapter = userListAdapter

        sharedViewModel.getListUser(PAGE_COUNT_REQUEST) // get data from api start with page 1

        observer()
        handleScrollPagination()
        handleSwipeToRefresh()
    }

    private fun observer() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.thirdScreenUiState.collectLatest { uiState ->
                    // update total pages
                    uiState.totalPages?.let {
                        TOTAL_PAGES = it
                    }

                    uiState.currentPage?.let {
                        CURRENT_PAGES = it
                    }

                    // if success and current page > 1, add data to list and update
                    if(uiState.isSuccess && CURRENT_PAGES > 1) {
                        //Toast.makeText(requireActivity(), "update", Toast.LENGTH_SHORT).show()
                        addNewUsersDataToRV(uiState.userData!!)
                        sharedViewModel.resetUiState()
                    }

                    // if success and current page == 1, submit data to list (replace)
                    if(uiState.isSuccess && CURRENT_PAGES == 1) {
                        //Toast.makeText(requireActivity(), "Fresh data", Toast.LENGTH_SHORT).show()
                        userListAdapter.submitList(uiState.userData)
                        sharedViewModel.resetUiState()
                    }

                    // handle error message
                    uiState.message?.let {
                        Snackbar.make(requireView(), uiState.message, Snackbar.LENGTH_LONG)
                            .setAction("Try Again") {
                                sharedViewModel.getListUser(PAGE_COUNT_REQUEST)
                            }.show()
                        sharedViewModel.messageThirdScreenShown()
                    }

                    // detect refresh state
                    if (uiState.isRefresh) {
                        sharedViewModel.getListUser(PAGE_COUNT_REQUEST) // trigger getlistuser again
                    }

                    // handle loading state
                    if(uiState.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.progressBarItem.setVisibility(View.VISIBLE)
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.progressBarItem.visibility = View.GONE
                    }

                    // handle if api call not success (means error), show empty data state
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
                    if(!binding.pullToRefreshRV.isRefreshing) { // check if user refresh or not
                        if(!canScrollDownMore && PAGE_COUNT_REQUEST < TOTAL_PAGES) {
                            PAGE_COUNT_REQUEST++
                            sharedViewModel.getListUser(PAGE_COUNT_REQUEST)
                        } else if (!canScrollDownMore && PAGE_COUNT_REQUEST >= TOTAL_PAGES) {
                            Toast.makeText(requireActivity(), "You've reached max pages!", Toast.LENGTH_SHORT).show()
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

    private fun handleSwipeToRefresh() {
        binding.pullToRefreshRV.setOnRefreshListener(OnRefreshListener {
            PAGE_COUNT_REQUEST = 1 // reset page count request to 1
            sharedViewModel.beginRefreshStateThirdScreen()
            binding.pullToRefreshRV.isRefreshing = false // set refresh state to false
        })
    }

    override fun onUserClicked(userData: UserData) {
        sharedViewModel.setSelectedUserName("${userData.firstName} ${userData.lastName}")
        findNavController().popBackStack()
        Toast.makeText(requireActivity(), "User selected!", Toast.LENGTH_SHORT).show()
    }
}