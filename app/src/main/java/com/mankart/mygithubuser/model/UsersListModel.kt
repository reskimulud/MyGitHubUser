package com.mankart.mygithubuser.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersListModel(
	@field:SerializedName("items")
	val items: ArrayList<UserModel>
) : Parcelable
