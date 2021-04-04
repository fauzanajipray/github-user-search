package com.dicoding.faprayyy.githubuser.view.moredetailuiser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.databinding.FollowerFollowingFragmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FollowerFollowingFragment : Fragment() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        val TAG = FollowerFollowingFragment::class.java.simpleName
        var EXTRA_USERNAME = "username"
        var EXTRA_POSITION = 0
    }

    private var _binding: FollowerFollowingFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FollowerFollowingFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        EXTRA_USERNAME = ""
        return view
    }

    private fun setupToolbar() {
        binding.toolbarId.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                getActivity()?.onBackPressed();
            }
        })
    }

    private fun setupTabLayout() {
        val sectionPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text= resources.getString(TAB_TITLES[position])
            tab.
        }.attach()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        EXTRA_USERNAME = FollowerFollowingFragmentArgs.fromBundle(arguments as Bundle).userName
        EXTRA_POSITION = FollowerFollowingFragmentArgs.fromBundle(arguments as Bundle).position
        Log.d(TAG, "DATA : $EXTRA_USERNAME, $EXTRA_POSITION")
        setupTabLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

}