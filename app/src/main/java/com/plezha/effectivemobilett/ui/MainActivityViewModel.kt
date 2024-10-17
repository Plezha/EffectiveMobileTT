package com.plezha.effectivemobilett.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plezha.data.repository.VacancySearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val vacancySearchRepository: VacancySearchRepository
) : ViewModel() {

    private val _favoritesAmount = MutableStateFlow<Int>(0)
    val favoritesAmount: StateFlow<Int> = _favoritesAmount

    init {
        viewModelScope.launch(Dispatchers.IO) {
            vacancySearchRepository.fetchSearchData()
            collectSearchData()
        }
    }

    private suspend fun collectSearchData() {
        vacancySearchRepository.searchData.collect { searchData ->
            _favoritesAmount.value = searchData?.getOrNull()
                ?.vacancies?.filter { it.isFavorite == true }?.size ?: 0
        }
    }
}