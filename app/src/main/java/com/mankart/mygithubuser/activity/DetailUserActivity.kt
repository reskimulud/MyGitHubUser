package com.mankart.mygithubuser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.adapter.FollowTabPagerAdapter
import com.mankart.mygithubuser.databinding.ActivityDetailUserBinding
import com.mankart.mygithubuser.model.UsersModel
import java.text.DecimalFormat

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val PUT_EXTRA = "put_extra"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.content_tab_follower,
            R.string.content_tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<UsersModel>(PUT_EXTRA) as UsersModel

        binding.tvNameDetail.text = data.name
        binding.tvUsernameDetail.text = data.username
        Glide.with(this)
            .load(data.avatar)
            .apply(RequestOptions().override(400, 400))
            .into(binding.imgAvatar)
        binding.tvRepo.text = getDecimal(data.repository)
        binding.tvFollowers.text = getDecimal(data.follower)
        binding.tvFollowing.text = getDecimal(data.following)
        binding.tvCompany.text = data.company
        binding.tvLocation.text = data.location

        val followTabPagerAdapter = FollowTabPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = followTabPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.hide()
    }

    private fun getDecimal(n: Int) : String {
        val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val countNum: Long = n.toLong() ?: 0
        val sum = Math.floor(Math.log10(countNum.toDouble())).toInt()
        val base = sum / 3
        return if (sum >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(
                countNum / Math.pow(
                    10.0,
                    base * 3.toDouble()
                )
            ) + suffix[base]
        } else {
            DecimalFormat().format(countNum)
        }
    }
}