package com.bridge.global.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerClientConfig {
  companion object {
    val serverClientInterface: ServerClientInterface
      get() {
        val retrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ServerClientInterface::class.java)
      }

    private fun getBaseUrl(): String {
      return "https://reqres.in/api/"
    }
  }
}
