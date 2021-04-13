package com.dicoding.faprayyy.githubuser.view.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.databinding.ActivitySettingsBinding
import com.dicoding.faprayyy.githubuser.view.AboutMeActivity
import com.dicoding.faprayyy.githubuser.view.alarm.AlarmReceiver

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsPreferenceFragment())
                .commit()
        }
        binding.toolbarId.setNavigationOnClickListener { finish() }

    }

    class SettingsPreferenceFragment : PreferenceFragmentCompat() {
        private lateinit var ABOUT_ME: String
        private lateinit var NOTIFICATIONS: String
        private lateinit var LANGUAGE: String
        private lateinit var languagePreference: Preference
        private lateinit var notificationPreference: SwitchPreferenceCompat
        private lateinit var aboutPreference: Preference
        private lateinit var alarmReceiver: AlarmReceiver
        private lateinit var mContext: Context

        override fun onAttach(context: Context) {
            super.onAttach(context)
            mContext = context
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.setting_preferences)
            init()
        }

        private fun init() {
            NOTIFICATIONS = resources.getString(R.string.key_notification)
            LANGUAGE = resources.getString(R.string.key_language)
            ABOUT_ME = resources.getString(R.string.key_about_me)
            languagePreference = findPreference<Preference>(LANGUAGE) as Preference
            aboutPreference = findPreference<Preference>(ABOUT_ME) as Preference
            notificationPreference = findPreference<SwitchPreferenceCompat>(NOTIFICATIONS) as SwitchPreferenceCompat
            alarmReceiver = AlarmReceiver()
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            languagePreference.setOnPreferenceClickListener {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
                true
            }

            aboutPreference.setOnPreferenceClickListener {
                val mIntent = Intent(activity, AboutMeActivity::class.java)
                startActivity(mIntent)
                true
            }

            notificationPreference.setOnPreferenceChangeListener { _, state ->
                if(state as Boolean){
                    Log.d("TAG", "Alarm nyala : $state")
                    alarmReceiver.setRepeatingAlarm(mContext)
                } else {
                    Log.d("TAG", "Switch false : $state")
                    alarmReceiver.cancelAlarm(mContext)
                }
                true
            }
        }
    }

}