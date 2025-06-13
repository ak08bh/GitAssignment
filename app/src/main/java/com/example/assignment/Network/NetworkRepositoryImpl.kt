package com.example.assignment.Network

import androidx.lifecycle.LiveData
import com.example.assignment.model.GitRepoModel
import com.example.assignment.model.Item
import retrofit2.Response

interface NetworkRepositoryImpl {

    suspend fun getRepoList() : Response<GitRepoModel>

    suspend fun insertListData(items: List<Item>)

    suspend fun getCountOfProducts(): Int

    fun getListDataRoom() : LiveData<List<Item>>

    fun getSearchData(query: String) : LiveData<List<Item>>
}