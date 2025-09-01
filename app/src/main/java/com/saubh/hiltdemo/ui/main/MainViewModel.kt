package com.saubh.hiltdemo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saubh.hiltdemo.data.model.ApiResponseItem
import com.saubh.hiltdemo.data.remote.TestApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val testApi: TestApi) : ViewModel() {

    private val _response = MutableStateFlow<List<ApiResponseItem>?>(null)
    val response: StateFlow<List<ApiResponseItem>?> = _response

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchData() {
        viewModelScope.launch {
            try {
                val result = testApi.getObjects()
                if (result.isSuccessful) {
                    _response.value = result.body()
                } else {
                    _error.value = "Error: ${result.code()} ${result.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            }
        }
    }
}
