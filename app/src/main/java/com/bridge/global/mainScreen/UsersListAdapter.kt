package com.bridge.global.mainScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bridge.global.R
import com.bridge.global.databinding.UserAdapterRowBinding
import com.bridge.global.model.Datum


class UsersListAdapter(private val context: Context?, private val list: List<Datum?>?) :
  RecyclerView.Adapter<UsersListAdapter.UserViewHolder>() {

  class UserViewHolder(private val binding: UserAdapterRowBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: Datum) {
      binding.user = user
    }
  }

  override fun getItemCount(): Int {
    return list?.size ?: 0
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
    val binding: UserAdapterRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
      R.layout.user_adapter_row, parent, false)
    return UserViewHolder(binding)
  }

  override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
    val item = list?.get(position)
    if (item != null) holder.bind(item)
  }
}
