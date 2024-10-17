package com.plezha.feature_search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plezha.data.model.Offer
import com.plezha.data.model.Vacancy
import com.plezha.data.repository.VacancySearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val vacancySearchRepository: VacancySearchRepository
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchScreenState>(SearchScreenState.Loading)
    val searchScreenState: StateFlow<SearchScreenState> = _searchScreenState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            collectSearchData()
        }
    }

    fun setIsFullListShown(isFullListShown: Boolean) {
        val value = _searchScreenState.value
        if (value is SearchScreenState.Success) {
            _searchScreenState.value = value.copy(
                isFullListShown = isFullListShown
            )
        }
    }

    fun changeFavoriteStatus(vacancy: Vacancy, newStatus: Boolean) {
        vacancySearchRepository.changeFavoriteStatus(vacancy, newStatus)
    }

    private suspend fun collectSearchData() {
        vacancySearchRepository.searchData.collect { searchData ->
            if (searchData != null && searchData.isSuccess) {
                val result = searchData.getOrNull()!!
                val newIsFullListShown =
                    try {
                        (_searchScreenState.value as SearchScreenState.Success).isFullListShown
                    } catch(e: Exception) {
                        false
                    }
                _searchScreenState.value = SearchScreenState.Success(
                    result.offers,
                    result.vacancies,
                    newIsFullListShown
                )
            } else {
                _searchScreenState.value =
                    if (searchData == null) SearchScreenState.Loading
                    else SearchScreenState.Error(
                        searchData.exceptionOrNull()!!
                    )
            }
        }
    }
}

sealed interface SearchScreenState {
    data class Success(
        val offers: List<Offer>,
        val vacancies: List<Vacancy>,
        val isFullListShown: Boolean
    ) : SearchScreenState
    data class Error(val e: Throwable) : SearchScreenState
    data object Loading : SearchScreenState
}
