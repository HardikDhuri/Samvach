package com.example.samvach.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build.VERSION_CODES.S
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samvach.R
import com.example.samvach.databinding.ListUsersBinding
import com.example.samvach.databinding.ReceiverChatUiBinding
import com.example.samvach.databinding.SenderChatUiBinding
import com.example.samvach.models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

private const val SENT: Int = 0
private const val RECEIVED: Int = 1

class MessageAdapter(private var messages: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val auth = Firebase.auth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == SENT) {
            val binding = SenderChatUiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SenderViewHolder(binding)
        } else {
            val binding = ReceiverChatUiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceiverViewHolder(binding)
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == SENT) {
            with(holder as SenderViewHolder) {
                with(messages[position]) {
                    binding.senderMessageTv.text = message
                    val time = SimpleDateFormat("hh:mm aa")
                    binding.messageTime.text = time.format(timeStamp)
                }
            }
        } else {
            with(holder as ReceiverViewHolder) {
                with(messages[position]) {
                    binding.receiverMessageTv.text = message
                    val time = SimpleDateFormat("hh:mm aa")
                    binding.messageTime.text = time.format(timeStamp)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (messages[position].senderId == auth.uid){
            RECEIVED
        } else {
            SENT
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class SenderViewHolder(val binding: SenderChatUiBinding): RecyclerView.ViewHolder(binding.root)
    inner class ReceiverViewHolder(val binding: ReceiverChatUiBinding): RecyclerView.ViewHolder(binding.root)
}