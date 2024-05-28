package com.cha1se.githubsearcher.domain.models

data class Repo(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)