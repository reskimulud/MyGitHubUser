package com.mankart.mygithubuser.fragment

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceFragmentCompat
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.viewmodel.MainViewModel

class PreferenceFragment : PreferenceFragmentCompat() {
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        mainViewModel.changeActionBarTitle("Setting")
    }

}