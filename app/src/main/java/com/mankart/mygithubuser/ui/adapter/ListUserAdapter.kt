package com.mankart.mygithubuser.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.data.model.UserModel
import de.hdodenhof.circleimageview.CircleImageView

class ListUserAdapter(private val onFavoriteClicked: (UserModel) -> Unit): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private var listUser = ArrayList<UserModel>()
    private lateinit var onClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(username: String?)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername : TextView = itemView.findViewById(R.id.tv_username)
        var imgAvatar : CircleImageView = itemView.findViewById(R.id.img_avatar)
        var ivFav: ImageView = itemView.findViewById(R.id.iv_fav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_users, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        holder.apply {
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .placeholder(R.drawable.placeholder)
                .apply(RequestOptions().override(400, 400))
                .error(R.drawable.placeholder)
                .into(holder.imgAvatar)
            tvUsername.text = user.login
            val ivFav = ivFav
            if (user.isFavorite) {
                ivFav.setImageDrawable(ContextCompat.getDrawable(ivFav.context, R.drawable.ic_fav_yes))
            } else {
                ivFav.setImageDrawable(ContextCompat.getDrawable(ivFav.context, R.drawable.ic_fav_no))
            }

            ivFav.setOnClickListener {
                onFavoriteClicked(user)
            }
            itemView.setOnClickListener { this@ListUserAdapter.onClickCallback.onItemClicked(listUser[holder.adapterPosition].login) }
        }
    }

    override fun getItemCount(): Int = listUser.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onClickCallback = onItemClickCallback
    }

    fun clearData() {
        listUser.clear()
        notifyDataSetChanged()
    }

    fun setData(newList: ArrayList<UserModel>) {
        listUser.clear()
        listUser.addAll(newList)
        notifyDataSetChanged()
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserModel> =
            object : DiffUtil.ItemCallback<UserModel>() {
                override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
                    oldItem.isFavorite == newItem.isFavorite

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
                    oldItem == newItem
            }
    }
}
