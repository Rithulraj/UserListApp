package com.bridge.global.mainScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridge.global.model.Datum
import com.bridge.global.model.Users
import com.bridge.global.room.UserRepository
import com.bridge.global.server.ServerClientConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val userRepository: UserRepository?): ViewModel() {
  val userListLiveData = MutableLiveData<List<Datum?>?>()
  val userList: MutableList<Datum?> = mutableListOf()
  var page = 1
  var isLoading = false

  fun getUserList() {
    isLoading = true
    val clientInterface = ServerClientConfig.serverClientInterface
    val call = clientInterface.getUsers(page)
    call.enqueue(object : Callback<Users> {
      override fun onResponse(call: Call<Users>, response: Response<Users>) {
        isLoading = false
        if (response.isSuccessful) {
          val list = response.body()?.data
          if (list?.isNotEmpty() == true) {
            userRepository?.insertAllUsers(list)
            userList.addAll(list)
            userListLiveData.postValue(userList)
          }
        }
      }

      override fun onFailure(call: Call<Users>, t: Throwable) {
        isLoading = false
        if (page == 1) {
          val users = userRepository?.getAllUsers()
          if (users?.isNotEmpty() == true) {
            userListLiveData.postValue(users)
          }
        }
      }
    })
  }
}
