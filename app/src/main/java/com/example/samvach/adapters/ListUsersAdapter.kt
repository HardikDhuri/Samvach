package com.example.samvach.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samvach.ChatRoom
import com.example.samvach.R
import com.example.samvach.databinding.ListUsersBinding
import com.example.samvach.models.User

class ListUsersAdapter(private val userList: ArrayList<User>) : RecyclerView.Adapter<ListUsersAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ListUsersBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(userList[position]) {
                binding.rowUserNameTv.text = this.name
                Glide.with(holder.binding.root.context)
                    .load(this.profilePicture)
                    .into(binding.rowUserProfileImageView)

                binding.rowUser.setOnClickListener {
                    val intent = Intent(binding.root.context, ChatRoom::class.java)
                    intent.putExtra("name", this.name)
                    intent.putExtra("uid", this.uid)
                    binding.root.context.startActivity(intent)
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

}
