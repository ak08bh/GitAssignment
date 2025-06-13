package com.example.assignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment.dao.GitDao
import com.example.assignment.model.Item
import com.example.assignment.model.UserModel
import com.example.assignment.utils.Constants.DATABASE_NAME

@Database(entities = [Item::class] , version = 1)
abstract class GitDatabase : RoomDatabase() {

    abstract fun myDao() : GitDao

    companion object{

        @Volatile
        private var INSTANCE : GitDatabase? = null

        fun getDatabaseInstance(context: Context) : GitDatabase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val roomDatabaseInstance = Room.databaseBuilder(context,
                    GitDatabase::class.java,
                    DATABASE_NAME)
                    .build()

                INSTANCE= roomDatabaseInstance
                return roomDatabaseInstance
            }
        }
    }

}