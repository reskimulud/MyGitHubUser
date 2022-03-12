package com.mankart.mygithubuser.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mankart.mygithubuser.activity.DetailUserActivity
import com.mankart.mygithubuser.activity.dataStore
import com.mankart.mygithubuser.adapter.ListUserAdapter
import com.mankart.mygithubuser.data.datastore.SettingPreference
import com.mankart.mygithubuser.databinding.FragmentSearchBinding
import com.mankart.mygithubuser.viewmodel.MainViewModel
import com.mankart.mygithubuser.viewmodel.UserViewModel
import com.mankart.mygithubuser.viewmodel.ViewModelFactory

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var listUserAdapter: ListUserAdapter
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var mainViewModel: MainViewModel

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

        rvUser = binding.rvUsers
        rvUser.setHasFixedSize(true)

        val pref = SettingPreference.getInstance(requireActivity().dataStore)
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(pref))[MainViewModel::class.java]

        showRecycleList()
        initObserver()
    }

    private fun initObserver() {
        userViewModel.listUser.observe(requireActivity()) {
            listUserAdapter.setData(it)
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
        listUserAdapter = ListUserAdapter()
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