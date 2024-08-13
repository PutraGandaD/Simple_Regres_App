package com.putragandad.simpleregresapp.presentation.fragment.initial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.putragandad.simpleregresapp.presentation.R
import com.putragandad.simpleregresapp.presentation.databinding.FragmentInitialScreenBinding

class InitialScreenFragment : Fragment() {
    private var _binding: FragmentInitialScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInitialScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_initialScreenFragment_to_secondScreenFragment)
        }
    }
}