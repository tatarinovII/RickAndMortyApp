package com.my.rickandmortyapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.domain.models.Character
import com.my.domain.usecases.GetNextPageUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getNextPageUseCase: GetNextPageUseCase
) : ViewModel() {

    private val _characterList = MutableLiveData<List<Character>>(emptyList())
    val characterList: LiveData<List<Character>> = _characterList

    private var currentPage: Int = 1
    private var isLoading: Boolean = false

    init {
        loadPage()
    }

    fun loadPage() {
        if (isLoading) return
        viewModelScope.launch {
            isLoading = true
            try {
                val newItems = getNextPageUseCase.execute(currentPage, emptyMap())
                val currentList = _characterList.value ?: emptyList()
                _characterList.value = currentList + newItems
                currentPage++
            } catch (e: Exception) {
                Log.e("!!!", e.toString())
            } finally {
                isLoading = false
            }
        }
    }

}