package com.mankart.mygithubuser.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersListModel(
	@field:SerializedName("items")
	val items: List<UserModel>
) : Parcelable
