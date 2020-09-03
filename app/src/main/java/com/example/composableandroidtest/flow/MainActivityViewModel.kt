package com.example.composableandroidtest.flow

import androidx.lifecycle.*
import com.example.composableandroidtest.model.Address
import com.example.composableandroidtest.dispatcher.AppDispatchers
import com.example.composableandroidtest.dispatcher.IAppDispatchers
import com.example.composableandroidtest.network.config.model.Resource
import com.example.composableandroidtest.network.config.model.Status
import com.example.composableandroidtest.network.service.backend.BackendService
import com.example.composableandroidtest.network.service.backend.IBackendService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel(
        private val dispatchers: IAppDispatchers,
        private val service: IBackendService
): ViewModel() {

    val cep: MutableLiveData<String> = MutableLiveData("")

    private val addressResource: MutableLiveData<Resource<Address>> = MutableLiveData()
    val isLoading: LiveData<Boolean> = Transformations.map(addressResource) {
        it?.status == Status.LOADING
    }
    val address: LiveData<Address> = Transformations.map(addressResource) {
        it?.data
    }
    val addressError: LiveData<String> = Transformations.map(addressResource) {
        it?.message
    }

    fun getAddress() {
        cep.value?.let { cep ->
            viewModelScope.launch(dispatchers.io) {
                service.getAddress(cep).collect {
                    addressResource.postValue(it)
                }
            }
        }
    }
}