package com.example.samvach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samvach.R
import com.example.samvach.adapters.ListUsersAdapter
import com.example.samvach.adapters.MessageAdapter
import com.example.samvach.databinding.ActivityChatRoomBinding
import com.example.samvach.main.MainActivity
import com.example.samvach.models.Message
import com.example.samvach.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat_room.*
import kotlinx.android.synthetic.main.activity_chat_room.view.*
import java.util.Date

class ChatRoom : AppCompatActivity() {

    private lateinit var binding: ActivityChatRoomBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        messageList = arrayListOf()
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderRoom = (auth.currentUser!!.uid + receiverUid )
        val receiverRoom = (receiverUid + auth.currentUser!!.uid)
        database = FirebaseDatabase.getInstance()
        binding.chatRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false )

        val ref = database.getReference("chats")
            .child(senderRoom)
            .child("messages")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                if (snapshot.exists()) {
                    for (messageSnapshot in snapshot.children) {
                        val message = messageSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    binding.chatRv.adapter = MessageAdapter(messageList)
                    binding.chatRv.scrollToPosition(messageList.size-1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.sendButton.setOnClickListener {
            val messageText = binding.messageEtv.text.toString()
            binding.messageEtv.text = null
            val date = Date().time
            val ref = database.reference.child("chats").child("messages")

            val message = Message(
                messageText,
                date,
                receiverUid,
            )
            database.reference.child("chats")
                .child(senderRoom)
                .child("messages")
                .push()
                .setValue(message).addOnSuccessListener {

                    database.reference.child("chats")
                        .child(receiverRoom)
                        .child("messages")
                        .push()
                        .setValue(message).addOnSuccessListener {

                        }
                }
        }

        topAppBar.title = name

        topAppBar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.details -> {
                    Toast.makeText(this, "Details Pressed", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.add_hide_user -> {
                    Toast.makeText(this, "Hide Pressed", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.add_verified_user -> {
                    Toast.makeText(this, "Verified Pressed", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.block_user -> {
                    Toast.makeText(this, "Block Pressed", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}