package com.putragandad.simpleregresapp.presentation.fragment.third

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.putragandad.simpleregresapp.domain.models.UserData
import com.putragandad.simpleregresapp.presentation.R
import com.putragandad.simpleregresapp.presentation.adapter.OnItemClickListener
import com.putragandad.simpleregresapp.presentation.adapter.UserListAdapter
import com.putragandad.simpleregresapp.presentation.databinding.FragmentSecondScreenBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUserData.adapter = userListAdapter
        sharedViewModel.getListUser(1)

        observer()
    }

    private fun observer() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.thirdScreenUiState.collectLatest { uiState ->
                    if(uiState.userData!!.isNotEmpty()) {
                        userListAdapter.submitList(uiState.userData)
                    }

                    if(uiState.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onUserClicked(userData: UserData) {
        TODO("Not yet implemented")
    }
}