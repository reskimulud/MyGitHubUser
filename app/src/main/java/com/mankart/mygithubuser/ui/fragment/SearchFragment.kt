package com.mankart.mygithubuser.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mankart.mygithubuser.ui.activity.DetailUserActivity
import com.mankart.mygithubuser.ui.adapter.ListUserAdapter
import com.mankart.mygithubuser.data.model.UserModel
import com.mankart.mygithubuser.data.viewmodel.FavUserViewModel
import com.mankart.mygithubuser.databinding.FragmentSearchBinding
import com.mankart.mygithubuser.data.viewmodel.UserViewModel
import com.mankart.mygithubuser.data.viewmodel.ViewModelFactory

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var factory: ViewModelFactory
    private val favUserViewModel: FavUserViewModel by activityViewModels { factory }
    private val userViewModel: UserViewModel by activityViewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        rvUser = binding.rvUsers
        rvUser.setHasFixedSize(true)

        showRecycleList()
        initObserver()
    }

    private fun initObserver() {
        userViewModel.listUser.observe(requireActivity()) {
            it.peekContent().let { user ->
                listUserAdapter.setData(user as ArrayList<UserModel>)
            }
        }
        userViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
        userViewModel.messageToast.observe(requireActivity()) {
            showToast(it)
        }
    }

    private fun showRecycleList() {
        rvUser.layoutManager = LinearLayoutManager(activity)
        listUserAdapter = ListUserAdapter { user ->
            if (user.isFavorite) {
                Log.e("FAV", "Set to No Fav")
                favUserViewModel.deleteFavUser(user)
            } else {
                Log.e("FAV", "Set to Fav")
                favUserViewModel.insertFavUser(user)
            }
        }
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String?) {
                val intent = Intent(activity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.PUT_EXTRA, username)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String, long: Boolean = true) {
        Toast.makeText(activity, message, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }
}