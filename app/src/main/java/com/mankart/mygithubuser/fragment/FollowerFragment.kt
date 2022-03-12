package com.mankart.mygithubuser.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mankart.mygithubuser.activity.DetailUserActivity
import com.mankart.mygithubuser.adapter.ListUserAdapter
import com.mankart.mygithubuser.databinding.FragmentFollowerBinding
import com.mankart.mygithubuser.viewmodel.UserViewModel


class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var tab: String
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tab = TABS[arguments?.getInt(ARG_SECTION_NUMBER, 0)!!.toInt()]
        val username = arguments?.getString(USERNAME)

        initObserver()

        getUserFollow(tab, username)
        showRecycleList()
    }

    private fun initObserver() {
        userViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
        userViewModel.messageToast.observe(requireActivity()) {
            showToast(it)
        }
        when (tab) {
            TABS[0] -> {
                userViewModel.userFollower.observe(requireActivity()) {
                    listUserAdapter.setData(it)
                }
            }
            TABS[1] -> {
                userViewModel.userFollowing.observe(requireActivity()) {
                    listUserAdapter.setData(it)
                }
            }
        }
    }

    private fun getUserFollow(tab: String, username: String?) {
        userViewModel.getUserFollow(tab, username)
    }

    private fun showRecycleList() {
        binding.rvFollow.layoutManager = LinearLayoutManager(context)
        listUserAdapter = ListUserAdapter()
        binding.rvFollow.adapter = listUserAdapter

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
        Toast.makeText(context, message, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    companion object {
        val TABS = listOf("followers", "following")
        const val ARG_SECTION_NUMBER ="tab_number"
        const val USERNAME = "username"
    }
}