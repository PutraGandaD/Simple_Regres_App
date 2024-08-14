package com.putragandad.simpleregresapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finsera.common.utils.network.ConnectivityManager
import com.putragandad.simpleregresapp.common.Resource
import com.putragandad.simpleregresapp.domain.usecase.CheckPalindromeUseCase
import com.putragandad.simpleregresapp.domain.usecase.GetListUserUseCase
import com.putragandad.simpleregresapp.presentation.fragment.third.ThirdScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SharedViewModel(
    private val connectivityManager: ConnectivityManager,
    private val checkPalindromeUseCase: CheckPalindromeUseCase,
    private val getListUserUseCase: GetListUserUseCase
) : ViewModel() {
    private val _initialScreenName = MutableLiveData<String>()
    val initialScreenName : LiveData<String>
        get() = _initialScreenName

    private val _selectedUserName = MutableLiveData<String>()
    val selectedUserName: LiveData<String>
        get() = _selectedUserName

    private val _thirdScreenUiState = MutableStateFlow(ThirdScreenUiState())
    val thirdScreenUiState = _thirdScreenUiState.asStateFlow()


    fun setInitialScreenName(name: String) {
        _initialScreenName.value = name
    }

    fun setSelectedUserName(name: String) {
        _selectedUserName.value = name
    }

    fun checkPalindrome(word: String) : Boolean {
        return checkPalindromeUseCase.invoke(word)
    }

    fun getListUser(page: Int) = viewModelScope.launch {
        if(connectivityManager.hasInternetConnection()) {
            getListUserUseCase.invoke(page).collectLatest { result ->
                when(result) {
                    is Resource.Loading -> {
                        _thirdScreenUiState.update { uiState ->
                            uiState.copy(
                                totalPages = null,
                                currentPage = null,
                                userData = emptyList(),
                                isLoading = true,
                                isSuccess = false,
                                isError = false,
                                isRefresh = false,
                                message = null)
                        }
                    }
                    is Resource.Success -> {
                        _thirdScreenUiState.update { uiState ->
                            uiState.copy(
                                totalPages = result.data?.totalPages,
                                currentPage = result.data?.currentPage,
                                userData = result.data?.data,
                                isLoading = false,
                                isSuccess = true,
                                isRefresh = false,
                                isError = false,
                                message = null)
                        }
                    }
                    is Resource.Error -> {
                        _thirdScreenUiState.update { uiState ->
                            uiState.copy(
                                totalPages = null,
                                currentPage = null,
                                userData = emptyList(),
                                isLoading = false,
                                isSuccess = false,
                                isError = true,
                                isRefresh = false,
                                message = result.message)
                        }
                    }
                }
            }
        } else {
            _thirdScreenUiState.update { uiState ->
                uiState.copy(totalPages = null, isError = true, userData = emptyList(), isLoading = false, isSuccess = false, message = "Tidak ada koneksi internet")
            }
        }

    }

    fun messageThirdScreenShown() {
        _thirdScreenUiState.update { currentUiState ->
            currentUiState.copy(message = null)
        }
    }

    fun resetUiState() {
        // reset state
        _thirdScreenUiState.update { currentUiState ->
            currentUiState.copy(totalPages = null, currentPage = null, userData = emptyList(), isLoading = false, isSuccess = false, isError = true, isRefresh = false, message = null)
        }
    }

    fun beginRefreshStateThirdScreen() {
        _thirdScreenUiState.update { currentUiState ->
            currentUiState.copy(isRefresh = true)
        }
    }
}