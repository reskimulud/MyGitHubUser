package com.mankart.mygithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.model.RepoModel

class ListRepoAdapter: RecyclerView.Adapter<ListRepoAdapter.ListViewHolder>() {
    private var listRepo = ArrayList<RepoModel>()

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvFullname: TextView = itemView.findViewById(R.id.tv_repo_fullname)
        var tvName: TextView = itemView.findViewById(R.id.tv_repo_name)
        var tvDescription: TextView = itemView.findViewById(R.id.tv_repo_desc)
        var tvLanguage: TextView = itemView.findViewById(R.id.tv_repo_language)
        var tvStar: TextView = itemView.findViewById(R.id.tv_repo_star)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_repo, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val repo = listRepo[position]

        holder.tvFullname.text = repo.fullName
        holder.tvName.text = repo.name
        holder.tvDescription.text = repo.description
        holder.tvLanguage.text = repo.language
        holder.tvStar.text = repo.stargazersCount.toString()
    }

    override fun getItemCount(): Int = listRepo.size

    fun clearData() {
        listRepo.clear()
        notifyDataSetChanged()
    }

    fun setData(newList: ArrayList<RepoModel>) {
        listRepo.clear()
        listRepo.addAll(newList)
        notifyDataSetChanged()
    }
}