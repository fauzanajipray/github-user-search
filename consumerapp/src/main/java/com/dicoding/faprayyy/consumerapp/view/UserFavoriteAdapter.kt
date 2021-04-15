package com.dicoding.faprayyy.consumerapp.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.faprayyy.consumerapp.R
import com.dicoding.faprayyy.consumerapp.data.UserFavorite
import com.dicoding.faprayyy.consumerapp.databinding.ItemUserBinding


class UserFavoriteAdapter : RecyclerView.Adapter<UserFavoriteAdapter.UserHolder>() {

    var mData = ArrayList<UserFavorite>()

    fun setData(items: List<UserFavorite>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(user: UserFavorite) {
            binding.tvName.text = user.name
            binding.tvUsername.text = user.username
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions().override(55,55))
                .into(binding.imgPhoto)
        }
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


}