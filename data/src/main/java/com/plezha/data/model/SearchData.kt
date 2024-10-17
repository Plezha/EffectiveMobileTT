package com.plezha.data.model

import com.google.gson.annotations.SerializedName


data class SearchData (

    @SerializedName("offers"    ) val offers    : List<Offer>   = arrayListOf(),
    @SerializedName("vacancies" ) val vacancies : List<Vacancy> = arrayListOf()

)