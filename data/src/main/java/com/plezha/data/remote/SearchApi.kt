package com.plezha.data.remote

import com.plezha.data.model.SearchData
import retrofit2.http.GET

interface SearchApi {
    @GET("uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download") // Замените на ваш endpoint
    suspend fun getSearchData(): SearchData
}