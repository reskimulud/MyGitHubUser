package com.mankart.mygithubuser.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.mankart.mygithubuser.R
import com.mankart.mygithubuser.ui.activity.dataStore
import com.mankart.mygithubuser.data.datastore.SettingPreference
import com.mankart.mygithubuser.data.viewmodel.MainViewModel
import com.mankart.mygithubuser.data.viewmodel.ViewModelFactory

class PreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var USERNAME: String
    private lateinit var NIGHT_MODE: String

    private lateinit var usernamePreference: EditTextPreference
    private lateinit var nightModePreference: SwitchPreference

    private lateinit var mainViewModel: MainViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        val pref = SettingPreference.getInstance(requireActivity().dataStore)
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(pref))[MainViewModel::class.java]

        init()
        initObserver()
    }

    private fun initObserver() {
        mainViewModel.getUsername().observe(requireActivity()) {
            if (it != null) {
                usernamePreference.summary = it
            } else {
                usernamePreference.summary = SettingPreference.DEFAULT_VAL
            }
        }
        mainViewModel.getThemeSetting().observe(requireActivity()) { isNightMode: Boolean ->
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            nightModePreference.isChecked = isNightMode
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun init() {
        USERNAME = getString(R.string.key_username)
        NIGHT_MODE = getString(R.string.key_night_mode)

        usernamePreference = findPreference<EditTextPreference>(USERNAME) as EditTextPreference
        nightModePreference = findPreference<SwitchPreference>(NIGHT_MODE) as SwitchPreference
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            USERNAME -> {
                val summary = sharedPreferences.getString(USERNAME, SettingPreference.DEFAULT_VAL)
                usernamePreference.summary = summary
                if (summary != null) {
                    mainViewModel.saveUsername(summary)
                }
            }
            NIGHT_MODE -> {
                val isChecked = sharedPreferences.getBoolean(NIGHT_MODE, false)
                nightModePreference.isChecked = isChecked
                mainViewModel.saveThemeSetting(isChecked)
            }
        }
    }
}