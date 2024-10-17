package com.plezha.data.model

import com.google.gson.annotations.SerializedName


data class Address (

  @SerializedName("town"   ) val town   : String? = null,
  @SerializedName("street" ) val street : String? = null,
  @SerializedName("house"  ) val house  : String? = null

)