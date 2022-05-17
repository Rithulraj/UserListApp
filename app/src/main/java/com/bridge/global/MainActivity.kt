package com.bridge.global

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridge.global.model.Datum
import com.bridge.global.model.Users
import com.bridge.global.room.UserRepository
import com.bridge.global.server.ServerClientConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
  private var usersRecyclerView: RecyclerView? = null
  private var userList: MutableList<Datum?> = mutableListOf()
  private var adapter: UsersListAdapter? = null
  private var page = 1
  private var isLoading = false
  private var userRepository: UserRepository? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    userRepository = UserRepository(this)
    initRecyclerView()
    getUserList()
  }

  private fun initRecyclerView() {
    usersRecyclerView = findViewById(R.id.users_recycler_view)
    adapter = UsersListAdapter(this, userList)
    val lManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    usersRecyclerView?.layoutManager = lManager
    usersRecyclerView?.adapter = adapter
    usersRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val visibleItemCount = lManager.childCount
        val totalItemCount = lManager.itemCount
        val firstVisibleItemPosition = lManager.findFirstVisibleItemPosition()
        if (!isLoading) {
          if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
            page ++
            getUserList()
            Log.d("MainActivity", "Scrolling...")
          }
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })
  }

  private fun getUserList() {
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
            adapter?.notifyDataSetChanged()
          }
        }
      }

      override fun onFailure(call: Call<Users>, t: Throwable) {
        isLoading = false
        if (page == 1) {
          val users = userRepository?.getAllUsers()
          if (users?.isNotEmpty() == true) {
            userList.addAll(users)
            adapter?.notifyDataSetChanged()
          }
        }
      }
    })
  }
}