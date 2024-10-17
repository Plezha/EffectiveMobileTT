package com.plezha.data.repository

import com.plezha.data.model.SearchData
import com.plezha.data.model.Vacancy
import com.plezha.data.remote.SearchApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class VacancySearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : VacancySearchRepository {
    private val _searchData = MutableStateFlow<Result<SearchData>?>(null)
    override val searchData: StateFlow<Result<SearchData>?> = _searchData

    override suspend fun fetchSearchData() {
        _searchData.value = try {
            val response = searchApi.getSearchData()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun changeFavoriteStatus(vacancy: Vacancy, newStatus: Boolean) {
        val value = _searchData.value
        if (value?.isSuccess == true) {
            val vacancies = value.getOrNull()!!.vacancies.toMutableList()
            vacancies[vacancies.indexOf(vacancy)] = vacancy.copy(
                isFavorite = newStatus
            )
            _searchData.value = Result.success(
                value.getOrNull()!!.copy(
                    vacancies = vacancies
                )
            )
        }

    }
}

interface VacancySearchRepository {
    val searchData: StateFlow<Result<SearchData>?>
    suspend fun fetchSearchData()
    fun changeFavoriteStatus(vacancy: Vacancy, newStatus: Boolean)
}