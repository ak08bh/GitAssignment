package com.example.assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.ListItemBinding
import com.example.assignment.model.Item

class GitRepoListAdapter(private val context: Context) :
    ListAdapter<Item, GitRepoListAdapter.GitRepoListViewHolder>(DiffUtilCallback) {

    private lateinit var onClickListener: RepoClickListener

    fun setOnClickListener(listener: RepoClickListener) {
        this.onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoListViewHolder {
        return GitRepoListViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GitRepoListViewHolder, position: Int) {
        val data = getItem(position)

        holder.binding.gitId.text = data.id.toString()
        holder.binding.gitName.text = data.name
        holder.binding.gitUrl.text = data.html_url

        holder.binding.layout.setOnClickListener {
            onClickListener.onClick(data, position)
        }
    }

    class GitRepoListViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

}

interface RepoClickListener {
    fun onClick(data: Item?, position: Int)
}


