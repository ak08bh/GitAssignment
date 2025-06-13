package com.example.assignment

import android.app.Application
import android.content.Context
import com.example.assignment.Network.NetworkRepository
import com.example.assignment.Network.NetworkService
import com.example.assignment.database.GitDatabase
import dagger.hilt.android.qualifiers.ApplicationContext

class GitApplication : Application() {

    lateinit var networkRepository : NetworkRepository

    override fun onCreate() {
        super.onCreate()

        val gitDatabase = GitDatabase.getDatabaseInstance(applicationContext)

        networkRepository = NetworkRepository(NetworkService.networkInterface, gitDatabase.myDao())

    }

}