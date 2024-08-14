package com.putragandad.simpleregresapp.presentation.fragment.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.putragandad.simpleregresapp.presentation.R
import com.putragandad.simpleregresapp.presentation.databinding.FragmentSecondScreenBinding
import com.putragandad.simpleregresapp.presentation.viewmodel.SharedViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel

class SecondScreenFragment : Fragment() {
    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel by koinNavGraphViewModel<SharedViewModel>(R.id.regresapp_navgraph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_secondScreenFragment_to_thirdScreenFragment)
        }

        observeInitialScreenName()
        observeSelectedUserName()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeInitialScreenName() {
        sharedViewModel.initialScreenName.observe(viewLifecycleOwner) { name ->
            binding.tvName.text = name
        }
    }

    private fun observeSelectedUserName() {
        sharedViewModel.selectedUserName.observe(viewLifecycleOwner) { name ->
            name?.let {
                binding.tvUserName.text = it
            } ?: run {
                binding.tvUserName.text = getString(R.string.choose_a_user_first_tv)
            }
        }
    }

}