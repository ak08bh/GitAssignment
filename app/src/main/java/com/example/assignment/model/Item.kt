package com.example.assignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.assignment.utils.Constants
import kotlinx.serialization.Serializable

@Entity(tableName = Constants.TABLE_NAME)
@Serializable
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String? = null,
    val html_url : String? = null
)