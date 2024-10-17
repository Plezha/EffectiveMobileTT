package com.plezha.data.model

import com.google.gson.annotations.SerializedName


data class Vacancy (

  @SerializedName("id"               ) val id               : String?           = null,
  @SerializedName("lookingNumber"    ) val lookingNumber    : Int?              = null,
  @SerializedName("title"            ) val title            : String?           = null,
  @SerializedName("address"          ) val address          : Address?          = Address(),
  @SerializedName("company"          ) val company          : String?           = null,
  @SerializedName("experience"       ) val experience       : Experience?       = Experience(),
  @SerializedName("publishedDate"    ) val publishedDate    : String?           = null,
  @SerializedName("isFavorite"       ) val isFavorite       : Boolean?          = null,
  @SerializedName("salary"           ) val salary           : Salary?           = Salary(),
  @SerializedName("schedules"        ) val schedules        : ArrayList<String> = arrayListOf(),
  @SerializedName("appliedNumber"    ) val appliedNumber    : Int?              = null,
  @SerializedName("description"      ) val description      : String?           = null,
  @SerializedName("responsibilities" ) val responsibilities : String?           = null,
  @SerializedName("questions"        ) val questions        : ArrayList<String> = arrayListOf()

)