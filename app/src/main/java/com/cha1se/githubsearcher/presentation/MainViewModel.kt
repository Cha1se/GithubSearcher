package com.cha1se.githubsearcher.presentation

import Content
import androidx.compose.material3.contentColorFor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cha1se.githubsearcher.data.GithubRepo
import com.cha1se.githubsearcher.domain.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(val dataRepo: GithubRepo) : ViewModel() {

    private val _repoList = MutableStateFlow<List<Item>>(emptyList())
    val repoList = _repoList.asStateFlow()
    var currentItem = MutableStateFlow(Item())
    var contentFromRepo = MutableStateFlow(listOf(Content()))
    var internetQueryJob: Job = Job()

    init {
        loadRepositories()
    }

    fun loadRepositories() {
        internetQueryJob = viewModelScope.launch {
            dataRepo.getRepositoryList().collect {
                _repoList.value = it.items
            }
        }
    }

    fun updateDataFromQuery(query: String) {
        if (internetQueryJob.isActive) {
            internetQueryJob.cancel()
        }
        internetQueryJob = viewModelScope.launch(Dispatchers.Default.limitedParallelism(1)) {
            delay(1000)
            if (!query.isNullOrEmpty()) {
                dataRepo.getRepositoryList(query.replace(" ", "+"), "best_match", "100", "1").collect {
                    println(it.items.size)
                    _repoList.value = it.items
                }
            } else {
                loadRepositories()
            }
        }
    }
    
    fun getRepositoryContent() {
        viewModelScope.launch {
            dataRepo.getContentInRepository(username = currentItem.value.owner.login, repo = currentItem.value.name, path = "").collect {
                println(it.first().name)
                contentFromRepo.value = it
            }
        }
    }

}