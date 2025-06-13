package com.example.assignment.Network

import androidx.lifecycle.LiveData
import com.example.assignment.dao.GitDao
import com.example.assignment.model.GitRepoModel
import com.example.assignment.model.Item
import retrofit2.Response

class NetworkRepository(private val networkInterface: NetworkInterface, val myDao: GitDao) : NetworkRepositoryImpl {

      override suspend fun getRepoList() : Response<GitRepoModel>{
          return networkInterface.getGitRepoList("language:swift","stars","desc")
     }

    override suspend fun insertListData(items: List<Item>) {
        myDao.insertListItem(items)
    }

     override suspend fun getCountOfProducts(): Int {
        return myDao.getCountOfProducts()
    }

     override fun getListDataRoom() : LiveData<List<Item>>{
        return myDao.getListItemRoom()
    }

    override fun getSearchData(query: String) : LiveData<List<Item>>{
        return myDao.getSearchQuery(query)
    }
}