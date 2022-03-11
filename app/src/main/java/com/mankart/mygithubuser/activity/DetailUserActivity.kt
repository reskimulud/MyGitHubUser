package com.mankart.mygithubuser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.adapter.FollowTabPagerAdapter
import com.mankart.mygithubuser.databinding.ActivityDetailUserBinding
import com.mankart.mygithubuser.model.UserModel
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<UserModel>(PUT_EXTRA) as UserModel
        setLayout(data)

        val followTabPagerAdapter = data.login?.let { FollowTabPagerAdapter(this, it) }
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = followTabPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.hide()
    }

    private fun setLayout(data: UserModel) {
        binding.apply {
            tvNameDetail.text = data.name
            tvUsernameDetail.text = data.login
            Glide.with(this@DetailUserActivity)
                .load(data.avatarUrl)
                .placeholder(R.drawable.placeholder)
                .apply(RequestOptions().override(400, 400))
                .error(R.drawable.placeholder)
                .into(imgAvatar)
            tvRepo.text = data.publicRepos?.let { getDecimal(it) }
            tvFollowers.text = data.followers?.let { getDecimal(it) }
            tvFollowing.text = data.following?.let { getDecimal(it) }
            tvCompany.text = data.company
            tvLocation.text = data.location
        }
    }

    private fun getDecimal(n: Int) : String {
        val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val countNum: Long = n.toLong()
        val sum = floor(log10(countNum.toDouble())).toInt()
        val base = sum / 3
        return if (sum >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(
                countNum / 10.0.pow(base * 3.toDouble())
            ) + suffix[base]
        } else {
            DecimalFormat().format(countNum)
        }
    }

    companion object {
        const val PUT_EXTRA = "put_extra"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.content_tab_follower,
            R.string.content_tab_following
        )
    }
}