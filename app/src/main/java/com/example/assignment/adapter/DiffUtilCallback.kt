package com.example.assignment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.assignment.model.GitRepoModel
import com.example.assignment.model.Item

object DiffUtilCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
