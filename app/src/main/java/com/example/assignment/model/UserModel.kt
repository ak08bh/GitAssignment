package com.example.assignment.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.assignment.utils.Constants.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String? = null,
    val html_url : String? = null
):Parcelable

