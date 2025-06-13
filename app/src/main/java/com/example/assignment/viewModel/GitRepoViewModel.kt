package com.example.assignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.Network.NetworkRepository
import com.example.assignment.Network.NetworkRepositoryImpl
import com.example.assignment.model.Item
import com.example.assignment.model.ResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GitRepoViewModel(private val networkRepository: NetworkRepositoryImpl) : ViewModel(){

    private val _list = MutableLiveData<ResponseModel<List<Item>>>()

    val list : LiveData<ResponseModel<List<Item>>> get() = _list

    private val _listItemRoom = MediatorLiveData<List<Item>>()

    val listItemRoom: LiveData<List<Item>> get() = _listItemRoom

    private var currentScore: LiveData<List<Item>>? = null

    init {
        val initialSource = networkRepository.getListDataRoom()
        currentScore = initialSource

        _listItemRoom.addSource(initialSource){
            _listItemRoom.value = it
        }
    }

    fun getRepoList(){
        viewModelScope.launch {
            val count = networkRepository.getCountOfProducts()
            if(count == 0) {
                try {
                    _list.value = ResponseModel.Loading(true)
                    val response = withContext(Dispatchers.IO) {
                        networkRepository.getRepoList()
                    }
                    if (response.isSuccessful) {
                        val items = response.body()?.items ?: emptyList()
                        _list.value = ResponseModel.Success(items)
                        networkRepository.insertListData(items)
                    } else {
                        _list.value = ResponseModel.Error(response.message())
                    }
                } catch (e: Exception) {
                    _list.value = ResponseModel.Error(e.localizedMessage ?: "Unknown error")
                }
            }
        }
    }

    fun getSearchData(query: String){

        val newSource: LiveData<List<Item>> = if (query.isEmpty()) {
            networkRepository.getListDataRoom()
        } else {
            networkRepository.getSearchData(query)
        }

        currentScore?.let{
            _listItemRoom.removeSource(currentScore!!)
        }

        currentScore = newSource

        _listItemRoom.addSource(newSource){
            _listItemRoom.value = it
        }
    }
}
