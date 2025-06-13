package com.example.assignment.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment.model.Item
import com.example.assignment.utils.Constants.TABLE_NAME

@Dao
interface GitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListItem(item: List<Item>)

    @Query("SELECT COUNT(*) FROM ${TABLE_NAME}")
    suspend fun getCountOfProducts() : Int

    @Query("SELECT * FROM ${TABLE_NAME}")
     fun getListItemRoom() : LiveData<List<Item>>

    @Query("""
    SELECT * FROM ${TABLE_NAME} 
    WHERE CAST(id AS TEXT) LIKE '%' || :query || '%' 
       OR name LIKE '%' || :query || '%'
""")
    fun getSearchQuery(query: String): LiveData<List<Item>>

}