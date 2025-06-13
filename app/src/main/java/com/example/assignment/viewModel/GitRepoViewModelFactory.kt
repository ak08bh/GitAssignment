package com.example.assignment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.Network.NetworkRepository

class GitRepoViewModelFactory(private val networkRepository: NetworkRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GitRepoViewModel(networkRepository) as T
    }
}