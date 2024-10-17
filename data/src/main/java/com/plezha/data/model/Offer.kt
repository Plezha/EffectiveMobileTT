package com.plezha.data.model

import com.google.gson.annotations.SerializedName


data class Offer (

  @SerializedName("id"    )   val id    : String? = null,
  @SerializedName("title" )   val title : String? = null,
  @SerializedName("button" )  val button: Button? = null,
  @SerializedName("link"  )   val link  : String? = null

)