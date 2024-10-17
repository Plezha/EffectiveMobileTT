package com.plezha.data

import com.plezha.data.remote.SearchApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiUnitTest {
    private val searchApi: SearchApi = Retrofit.Builder()
        .baseUrl("https://drive.usercontent.google.com/u/0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SearchApi::class.java)

    @Test
    fun getSearchData_returnsData() = runBlocking {
        val response = searchApi.getSearchData()

        assertTrue(response.vacancies.isNotEmpty())
        assertTrue(response.offers.isNotEmpty())
    }
}