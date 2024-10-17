package com.plezha.feature_favorites.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plezha.data.model.Vacancy
import com.plezha.data.repository.VacancySearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val vacancySearchRepository: VacancySearchRepository
) : ViewModel() {

    private val _favoritesScreenState = MutableStateFlow<FavoritesScreenState>(FavoritesScreenState.Loading)
    val favoritesScreenState: StateFlow<FavoritesScreenState> = _favoritesScreenState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            collectSearchData()
        }
    }

    fun changeFavoriteStatus(vacancy: Vacancy, newStatus: Boolean) {
        vacancySearchRepository.changeFavoriteStatus(vacancy, newStatus)
    }

    private suspend fun collectSearchData() {
        vacancySearchRepository.searchData.collect { searchData ->
            if (searchData != null && searchData.isSuccess) {
                val result = searchData.getOrNull()!!
                _favoritesScreenState.value = FavoritesScreenState.Success(
                    result.vacancies.filter { it.isFavorite == true },
                )
            } else {
                _favoritesScreenState.value =
                    if (searchData == null) FavoritesScreenState.Loading
                    else FavoritesScreenState.Error(
                        searchData.exceptionOrNull()!!
                    )
            }
        }
    }
}

sealed interface FavoritesScreenState {
    data class Success(
        val vacancies: List<Vacancy>,
    ) : FavoritesScreenState
    data class Error(val e: Throwable) : FavoritesScreenState
    data object Loading : FavoritesScreenState
}