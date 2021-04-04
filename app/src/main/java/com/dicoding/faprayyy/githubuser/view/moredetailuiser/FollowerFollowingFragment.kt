package com.dicoding.faprayyy.githubuser.view.moredetailuiser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.StringRes
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.databinding.FollowerFollowingFragmentBinding
import com.dicoding.faprayyy.githubuser.databinding.UserSearchFragmentBinding

class FollowerFollowingFragment : Fragment() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3
        )
        val TAG = FollowerFollowingFragment::class.java.simpleName
    }

    private lateinit var viewModel: FollowerFollowingViewModel

    private var _binding: FollowerFollowingFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FollowerFollowingFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FollowerFollowingViewModel::class.java)

        val username = FollowerFollowingFragmentArgs.fromBundle(arguments as Bundle).userName
        Log.d(TAG, "$username")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarId.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                getActivity()?.onBackPressed();
            }
        })

    }

}