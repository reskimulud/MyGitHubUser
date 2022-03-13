package com.mankart.mygithubuser.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepoModel(

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("stargazers_count")
	val stargazersCount: Int? = 0,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("language")
	val language: String? = null
) : Parcelable
