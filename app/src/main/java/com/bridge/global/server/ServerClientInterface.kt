package com.bridge.global.server

import com.bridge.global.model.Users
import retrofit2.Call
import retrofit2.http.*

interface ServerClientInterface {
  @GET("users")
  fun getUsers(@Query("page") page: Int): Call<Users>
}
