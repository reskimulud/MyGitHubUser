package com.mankart.mygithubuser.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "fav_user")
@Parcelize
data class UserModel(

	@field:PrimaryKey(autoGenerate = false)
	@field:ColumnInfo(name = "id")
	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("followers")
	val followers: Int? = 0,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = "",

	@field:SerializedName("following")
	val following: Int? = 0,

	@field:ColumnInfo(name = "name")
	@field:SerializedName("name")
	val name: String? = "",

	@field:SerializedName("company")
	val company: String? = "",

	@field:SerializedName("location")
	val location: String? = "",

	@field:SerializedName("public_repos")
	val publicRepos: Int? = 0,

	@field:ColumnInfo(name = "login")
	@field:SerializedName("login")
	val login: String? = "",

	@field:ColumnInfo(name = "created_at")
	var createdAt: String? = "",

	var isFavorite: Boolean = false
) : Parcelable
