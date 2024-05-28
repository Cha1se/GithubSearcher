package com.cha1se.githubsearcher.data

import Content
import com.cha1se.githubsearcher.domain.models.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GithubRepo (val githubApi: GithubApi) {
   suspend fun getRepositoryList(query: String = "android", sort: String = "stars", perPage: String = "100", page: String = "1"): Flow<Repo> = flow {
       emit(githubApi.getRepositoriesByQuery(query, sort, perPage, page))
   }

    suspend fun getContentInRepository(username: String, repo: String, path: String): Flow<List<Content>> = flow {
        emit(githubApi.getContentInRepository(username = username, repo = repo, path = path).sortedBy { it.type })
    }
}