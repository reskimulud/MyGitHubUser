package com.mankart.mygithubuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.model.UserModel
import de.hdodenhof.circleimageview.CircleImageView

class ListUserAdapter: RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private var listUser = ArrayList<UserModel>()
    private lateinit var onClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(username: String?)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername : TextView = itemView.findViewById(R.id.tv_username)
        var imgAvatar : CircleImageView = itemView.findViewById(R.id.img_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_users, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(400, 400))
            .into(holder.imgAvatar)
        holder.tvUsername.text = user.login

        holder.itemView.setOnClickListener { this.onClickCallback.onItemClicked(listUser[holder.adapterPosition].login) }
    }

    override fun getItemCount(): Int = listUser.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        listUser.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: ArrayList<UserModel>) {
        listUser.clear()
        listUser.addAll(newList)
        notifyDataSetChanged()
    }
}
