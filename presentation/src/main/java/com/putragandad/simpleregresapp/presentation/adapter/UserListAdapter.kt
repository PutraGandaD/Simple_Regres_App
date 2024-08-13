package com.putragandad.simpleregresapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.putragandad.simpleregresapp.domain.models.UserData
import com.putragandad.simpleregresapp.presentation.databinding.ItemRvUserdataBinding

class UserListAdapter(
    val itemClickListener: OnItemClickListener) : ListAdapter<UserData, UserListAdapter.UserListItemViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListItemViewHolder {
        val binding = ItemRvUserdataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListItemViewHolder(binding)
    }

    class UserListItemViewHolder(private val binding: ItemRvUserdataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserData) {
            binding.tvUserName.text = "${data.firstName} ${data.lastName}"
            binding.tvUserEmail.text = data.email

            Glide
                .with(binding.root.context)
                .load(data.avatar)
                .centerCrop()
                .into(binding.ivUser)
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserData>(){
            override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem==newItem
            }
            override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem==newItem
            }
        }
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        val data = getItem(position)

        holder.bind(data)

        holder.itemView.setOnClickListener {
            itemClickListener.onUserClicked(data)
        }
    }
}

interface OnItemClickListener {
    fun onUserClicked(userData: UserData)
}