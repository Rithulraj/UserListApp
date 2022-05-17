package com.bridge.global.model

import com.google.gson.annotations.SerializedName

class Users {
  @SerializedName("page")
  var page: Int? = null

  @SerializedName("per_page")
  var perPage: Int? = null

  @SerializedName("total")
  var total: Int? = null

  @SerializedName("total_pages")
  var totalPages: Int? = null

  @SerializedName("data")
  var data: List<Datum>? = null

  @SerializedName("support")
  var support: Support? = null
}
