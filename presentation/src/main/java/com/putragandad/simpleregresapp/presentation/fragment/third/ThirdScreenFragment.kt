package com.putragandad.simpleregresapp.presentation.fragment.third

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.putragandad.simpleregresapp.presentation.R
import com.putragandad.simpleregresapp.presentation.databinding.FragmentSecondScreenBinding
import com.putragandad.simpleregresapp.presentation.databinding.FragmentThirdScreenBinding
import com.putragandad.simpleregresapp.presentation.viewmodel.SharedViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel

class ThirdScreenFragment : Fragment() {
    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel by koinNavGraphViewModel<SharedViewModel>(R.id.regresapp_navgraph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}