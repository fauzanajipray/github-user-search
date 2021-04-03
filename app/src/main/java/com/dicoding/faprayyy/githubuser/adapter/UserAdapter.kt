package com.dicoding.faprayyy.githubuser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.databinding.ItemUserBinding
import com.dicoding.faprayyy.githubuser.datamodel.UserModel

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val mData = ArrayList<UserModel>()

    fun setData(items: ArrayList<UserModel>) {
        mData.clear()
        mData.addAll(items)
        Log.d("CEK_DATASET", "$items")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(user: UserModel) {
            binding.tvName.text = user.name
            binding.tvUsername.text = user.username
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions().override(55,55))
                .into(binding.imgPhoto)
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserModel)
    }

}