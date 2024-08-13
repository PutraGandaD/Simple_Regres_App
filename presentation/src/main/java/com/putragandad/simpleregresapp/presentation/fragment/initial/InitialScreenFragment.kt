package com.putragandad.simpleregresapp.presentation.fragment.initial

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.putragandad.simpleregresapp.presentation.R
import com.putragandad.simpleregresapp.presentation.databinding.FragmentInitialScreenBinding
import com.putragandad.simpleregresapp.presentation.viewmodel.SharedViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.navigation.koinNavGraphViewModel

class InitialScreenFragment : Fragment() {
    private var _binding: FragmentInitialScreenBinding? = null
    private val binding get() = _binding!!

    // scope to navgraph
    private val sharedViewModel by koinNavGraphViewModel<SharedViewModel>(R.id.regresapp_navgraph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInitialScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pickMedia = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedPicUri = result.data?.data
                Glide.with(requireView())
                    .load(selectedPicUri)
                    .into(binding.icBtnSelectPhoto)
            }
        }

        binding.icBtnSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            pickMedia.launch(intent)
        }

        binding.btnCheckPalindrome.setOnClickListener {
            checkPalindrome()
        }

        binding.btnNext.setOnClickListener {
            navigateToSecondScreen()
        }

//        sharedViewModel.initialScreenName.observe(viewLifecycleOwner) { name ->
//            Toast.makeText(requireActivity(), name, Toast.LENGTH_SHORT).show()
//        }
    }

    private fun checkPalindrome() {
        val text = binding.etPalindrome.editText?.text.toString()
        if(text.isNotEmpty()) {
            val result = sharedViewModel.checkPalindrome(text)
            dialogPalindromeResult(result)
        } else {
            Snackbar.make(requireView(), "Palindrome column is required to be filled to continue check palindrome.", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun dialogPalindromeResult(result: Boolean) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.palindrome_check_result_dialog_title))
            .setMessage(
                if(result) {
                    getString(R.string.ispalindrome_result_text_dialog)
                } else {
                    getString(R.string.notpalindrome_result_text_dialog)
                }
            )
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToSecondScreen() {
        val name = binding.etName.editText?.text.toString()

        if(name.isNotEmpty()) {
            sharedViewModel.setInitialScreenName(name)
            findNavController().navigate(R.id.action_initialScreenFragment_to_secondScreenFragment)
        } else {
            Snackbar.make(requireView(), "Name column is required to be filled to continue.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}