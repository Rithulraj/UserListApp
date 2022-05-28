package com.bridge.global.mainScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridge.global.R
import com.bridge.global.databinding.ActivityMainBinding
import com.bridge.global.room.UserRepository

class MainActivity : AppCompatActivity() {
  private var usersRecyclerView: RecyclerView? = null
  private var adapter: UsersListAdapter? = null
  private lateinit var viewModel: MainViewModel
  private var binding: ActivityMainBinding? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    viewModel =
      ViewModelProvider(this, MainViewModelFactory(UserRepository(this))).get(
        MainViewModel::class.java
      )

    initRecyclerView()
    viewModel.getUserList()
    viewModel.userListLiveData.observe(this) {
      adapter?.itemCount?.let { it1 ->
        val positionStart = if (it1 > 0)  it1 - 1 else it1
        adapter?.notifyItemRangeChanged(positionStart, viewModel.userList.size)
      }
    }
  }

  private fun initRecyclerView() {
    if (!this::viewModel.isInitialized) return
    usersRecyclerView = binding?.usersRecyclerView
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
          }
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })
  }
}
