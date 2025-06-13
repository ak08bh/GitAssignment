package com.example.assignment.model

import kotlinx.serialization.Serializable

@Serializable
data class GitRepoModel(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)