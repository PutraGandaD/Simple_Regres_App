package com.putragandad.simpleregresapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putragandad.simpleregresapp.domain.usecase.CheckPalindromeUseCase
import com.putragandad.simpleregresapp.domain.usecase.GetListUserUseCase
import kotlinx.coroutines.launch

class SharedViewModel(
    private val checkPalindromeUseCase: CheckPalindromeUseCase,
    private val getListUserUseCase: GetListUserUseCase
) : ViewModel() {
    private val _initialScreenName = MutableLiveData<String>()
    val initialScreenName : LiveData<String>
        get() = _initialScreenName

    private val _selectedUserName = MutableLiveData<String>()
    val selectedUserName: LiveData<String>
        get() = _selectedUserName


    fun setInitialScreenName(name: String) {
        _initialScreenName.value = name
    }

    fun setSelectedUserName(name: String) {
        _selectedUserName.value = name
    }

    fun checkPalindrome(word: String) : Boolean {
        return checkPalindromeUseCase.invoke(word)
    }

}