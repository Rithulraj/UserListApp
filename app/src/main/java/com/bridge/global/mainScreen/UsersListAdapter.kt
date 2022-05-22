package com.bridge.global.mainScreen
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bridge.global.R
import com.bridge.global.model.Datum
import com.bumptech.glide.Glide

class UsersListAdapter(private val context: Context?, private val list: List<Datum?>?) :
  RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userImage: ImageView = itemView.findViewById(R.id.user_image)
    val name: TextView = itemView.findViewById(R.id.name)
    val email: TextView = itemView.findViewById(R.id.email)
  }

  override fun getItemCount(): Int {
    return list?.size ?: 0
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.user_adapter_row, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = list?.get(position)
    if (context != null) {
      Glide.with(context).load(item?.avatar).circleCrop().into(holder.userImage)
    }
    holder.name.text = context?.getString(R.string.first_name_last_name, item?.firstName, item?.lastName)
    holder.email.text = item?.email
  }
}
