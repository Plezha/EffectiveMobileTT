package com.plezha.data.model

import com.google.gson.annotations.SerializedName


data class Experience (

  @SerializedName("previewText" ) val previewText : String? = null,
  @SerializedName("text"        ) val text        : String? = null

)