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
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.activity.DetailUserActivity
import com.mankart.mygithubuser.activity.dataStore
import com.mankart.mygithubuser.adapter.ListUserAdapter
import com.mankart.mygithubuser.data.datastore.SettingPreference
import com.mankart.mygithubuser.databinding.FragmentHomeBinding
import com.mankart.mygithubuser.model.UserModel
import com.mankart.mygithubuser.viewmodel.MainViewModel
import com.mankart.mygithubuser.viewmodel.UserViewModel
import com.mankart.mygithubuser.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var listUserAdapter: ListUserAdapter
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
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
        initialUsers()
    }

    private fun initObserver() {
        userViewModel.listUser.observe(viewLifecycleOwner) {
            listUserAdapter.setData(it)
        }
        userViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        userViewModel.messageToast.observe(viewLifecycleOwner) {
            showToast(it)
        }
        userViewModel.user.observe(viewLifecycleOwner) {
            val moveIntent = Intent(activity, DetailUserActivity::class.java)
            moveIntent.putExtra(DetailUserActivity.PUT_EXTRA, it)
            startActivity(moveIntent)
        }
    }

    private fun showRecycleList() {
        rvUser.layoutManager = LinearLayoutManager(activity)
        listUserAdapter = ListUserAdapter()
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String?) {
                userViewModel.getUserByUsername(username)
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

    private fun initialUsers() {
        val users: ArrayList<UserModel> = arrayListOf(
            UserModel(
                0,
                "https://avatars.githubusercontent.com/u/63949402?v=4",
                0,
                "",
                "",
                "",
                0,
                "reskimulud"
            ),
            UserModel(
                0,
                "https://avatars.githubusercontent.com/u/69951585?v=4",
                0,
                "",
                "",
                "",
                0,
                "Ikram-Maulana"
            ),
            UserModel(
                0,
                "https://avatars.githubusercontent.com/u/49898183?v=4",
                0,
                "",
                "",
                "",
                0,
                "fauzywijaya"
            ),
            UserModel(
                0,
                "https://avatars.githubusercontent.com/u/37388666?v=4",
                0,
                "",
                "",
                "",
                0,
                "drajatdani1892"
            ),
            UserModel(
                0,
                "https://avatars.githubusercontent.com/u/69128801?v=4",
                0,
                "",
                "",
                "",
                0,
                "Deri-Kurniawan"
            ),
        )
        listUserAdapter.setData(users)
    }
}