package com.mankart.mygithubuser.ui.fragment

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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.ui.activity.dataStore
import com.mankart.mygithubuser.ui.adapter.ListRepoAdapter
import com.mankart.mygithubuser.data.datastore.SettingPreference
import com.mankart.mygithubuser.data.model.RepoModel
import com.mankart.mygithubuser.databinding.FragmentHomeBinding
import com.mankart.mygithubuser.data.viewmodel.MainViewModel
import com.mankart.mygithubuser.data.viewmodel.UserViewModel
import com.mankart.mygithubuser.data.viewmodel.ViewModelFactory
import com.mankart.mygithubuser.data.model.UserModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by activityViewModels { factory }
    private val userViewModel: UserViewModel by activityViewModels { factory }
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

        factory = ViewModelFactory.getInstance(requireActivity())

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
            it.peekContent().let { user ->
                setLayout(user)
                userViewModel.getListUserRepos(user.login)
            }
        }

        userViewModel.listRepo.observe(requireActivity()) {
            it.peekContent().let { repo ->
                listRepoAdapter.setData(repo as ArrayList<RepoModel>)
            }
        }

        userViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
        userViewModel.messageToast.observe(requireActivity()) {
            showToast(it)
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