package com.ravikant.randomapiwithretrofithiltexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravikant.randomapiwithretrofithiltexample.repository.MainRepository
import com.ravikant.randomapiwithretrofithiltexample.retrofit.AppInfoItem
import com.ravikant.randomapiwithretrofithiltexample.util.DispatcherProvider
import com.ravikant.randomapiwithretrofithiltexample.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {

    sealed class AppInfo {
        class SuccessSingleData(val data: AppInfoItem) : AppInfo()
        class SuccessListData(val data: List<AppInfoItem>) : AppInfo()
        class Failure(val error: String): AppInfo()
        object Loading : AppInfo()
        object Empty: AppInfo()
    }

    private val _appData = MutableStateFlow<AppInfo>(AppInfo.Empty)
    val appData get() = _appData

    fun getAppData() {
        viewModelScope.launch(dispatchers.io) {
            _appData.value = AppInfo.Loading
            when(val response = repository.getAppInfo()) {
                is Resource.Error -> {
                    _appData.value = AppInfo.Failure(response.message!!)
                }
                is Resource.Success -> {
                    _appData.value = AppInfo.SuccessSingleData(response.data!!)
                }
            }
        }
    }


    fun getAppListData(size: Int) {
        viewModelScope.launch(dispatchers.io) {
            _appData.value = AppInfo.Loading
            when(val response = repository.getMultipleAppInfo(size)) {
                is Resource.Error -> {
                    _appData.value = AppInfo.Failure(response.message!!)
                }
                is Resource.Success -> {
                    _appData.value = AppInfo.SuccessListData(response.data!!)
                }
            }
        }
    }
}