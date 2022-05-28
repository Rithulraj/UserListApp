package com.bridge.global.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class Datum(
  @PrimaryKey(autoGenerate = false)
  @SerializedName("id")
  var id: Int? = null,

  @SerializedName("email")
  var email: String? = null,

  @SerializedName("first_name")
  var firstName: String? = null,

  @SerializedName("last_name")
  var lastName: String? = null,

  @SerializedName("avatar")
  var avatar: String? = null
) {
  companion object {
    @JvmStatic
    @BindingAdapter("android:loadImage")
    fun loadImage(imageView: ImageView, imageURL: String?) {
      Glide.with(imageView.context).setDefaultRequestOptions(RequestOptions().circleCrop())
        .load(imageURL).into(imageView)
    }
  }
}
