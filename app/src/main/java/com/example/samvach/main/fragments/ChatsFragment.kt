package com.example.samvach.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.samvach.R
import com.example.samvach.adapters.ListUsersAdapter
import com.example.samvach.databinding.FragmentChatsBinding
import com.example.samvach.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class ChatsFragment : Fragment() {

    private lateinit var binding: FragmentChatsBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var usersArrayList: ArrayList<User>
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: ChatsViewModel

    companion object {
        fun newInstance() = ChatsFragment()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chats, container, false)
        binding = FragmentChatsBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        usersArrayList = arrayListOf()
        binding.listUsersRv.setHasFixedSize(true)
        getUserData()
    }


    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("users")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersArrayList.clear()
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        if (user!!.uid  != auth.uid) {
                            usersArrayList.add(user)
                        }
                    }
                    binding.listUsersRv.adapter = ListUsersAdapter(usersArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}