package com.bridge.global.mainScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridge.global.R
import com.bridge.global.room.UserRepository

class MainActivity : AppCompatActivity() {
  private var usersRecyclerView: RecyclerView? = null
  private var adapter: UsersListAdapter? = null
  private lateinit var  viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    viewModel =
      ViewModelProvider(this, MainViewModelFactory(UserRepository(this))).get(
        MainViewModel::class.java
      )

    initRecyclerView()
    viewModel.getUserList()
    viewModel.userListLiveData.observe(this, Observer{
      adapter?.notifyDataSetChanged()
    })
  }

  private fun initRecyclerView() {
    if (!this::viewModel.isInitialized) return
    usersRecyclerView = findViewById(R.id.users_recycler_view)
    adapter = UsersListAdapter(this, viewModel.userList)
    val lManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    usersRecyclerView?.layoutManager = lManager
    usersRecyclerView?.adapter = adapter
    usersRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val visibleItemCount = lManager.childCount
        val totalItemCount = lManager.itemCount
        val firstVisibleItemPosition = lManager.findFirstVisibleItemPosition()
        if (!viewModel.isLoading) {
          if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
            viewModel.page++
            viewModel.getUserList()
            Log.d("MainActivity", "Scrolling...")
          }
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })
  }
}