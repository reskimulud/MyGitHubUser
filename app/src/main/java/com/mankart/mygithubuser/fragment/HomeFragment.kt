package com.mankart.mygithubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.activity.MainActivity
import com.mankart.mygithubuser.activity.dataStore
import com.mankart.mygithubuser.adapter.ListRepoAdapter
import com.mankart.mygithubuser.data.datastore.SettingPreference
import com.mankart.mygithubuser.databinding.FragmentHomeBinding
import com.mankart.mygithubuser.viewmodel.MainViewModel
import com.mankart.mygithubuser.viewmodel.UserViewModel
import com.mankart.mygithubuser.viewmodel.ViewModelFactory
import com.mankart.mygithubuser.model.UserModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainViewModel: MainViewModel
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var rvRepo: RecyclerView
    private lateinit var listRepoAdapter: ListRepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvRepo = binding.rvRepo
        rvRepo.setHasFixedSize(true)

        val pref = SettingPreference.getInstance(requireActivity().dataStore)
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(pref))[MainViewModel::class.java]

        initLayout()
        initObserver()
        showRecycleList()
    }

    private fun showRecycleList() {
        rvRepo.layoutManager = LinearLayoutManager(activity)
        listRepoAdapter = ListRepoAdapter()
        rvRepo.adapter = listRepoAdapter
    }

    private fun initObserver() {
        mainViewModel.getUsername().observe(requireActivity()) {
            userViewModel.getUserByUsername(it)
        }

        userViewModel.user.observe(requireActivity()) {
            setLayout(it.peekContent())
        }
    }

    private fun initLayout() {
        binding.apply {
            Glide.with(requireActivity())
                .load(R.drawable.placeholder)
                .apply(RequestOptions().override(400, 400))
                .into(imgAvatar)
            name.text = "Set in setting"
            username.visibility = View.INVISIBLE
            following.visibility = View.INVISIBLE
            followers.visibility = View.INVISIBLE
            textView4.visibility = View.INVISIBLE
            textView5.visibility = View.INVISIBLE
        }
    }

    private fun setLayout(user: UserModel?) {
        binding.apply {
            Glide.with(requireActivity())
                .load(user?.avatarUrl)
                .placeholder(R.drawable.placeholder)
                .apply(RequestOptions().override(400, 400))
                .error(R.drawable.placeholder)
                .into(imgAvatar)
            username.text = user?.login
            name.text = user?.name
            followers.text = user?.followers.toString()
            following.text = user?.following.toString()
            totalRepo.text = user?.publicRepos.toString()

            username.visibility = View.VISIBLE
            following.visibility = View.VISIBLE
            followers.visibility = View.VISIBLE
            textView4.visibility = View.VISIBLE
            textView5.visibility = View.VISIBLE
        }
    }
}