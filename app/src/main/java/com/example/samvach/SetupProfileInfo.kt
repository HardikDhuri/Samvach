package com.example.samvach

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.text.set
import com.example.samvach.databinding.ActivitySetupProfileInfoBinding
import com.example.samvach.main.MainActivity
import com.example.samvach.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class SetupProfileInfo : AppCompatActivity() {

    private lateinit var binding: ActivitySetupProfileInfoBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var selectImage: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupProfileInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        binding.userProfileIv.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 45)
        }

        binding.profileSetupBtn.setOnClickListener {
            val name = binding.nameTv.text.toString()

            if (name.isEmpty()) {
                binding.nameTv.error = "Please enter your name."
            }
            if (selectImage != null) {

            }
            val reference =
                storage.reference
                    .child("Profiles")
                    .child(auth.uid.toString())
            reference.putFile(selectImage).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    reference.downloadUrl.addOnSuccessListener { uri ->
                        val image = uri.toString()
                        val uid = auth.uid.toString()
                        val email = auth.currentUser?.email.toString()
                        val user =
                            User(
                                email,
                                name,
                                image,
                                uid
                            )

                        database.reference
                            .child("users")
                            .child(uid)
                            .setValue(user)
                            .addOnSuccessListener {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finishAffinity()
                            }
                            .addOnFailureListener {
                                Log.i("Apple", "Didnt saved user data at Firebase: $user")
                            }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                binding.userProfileIv.setImageURI(data.data)
                selectImage = data.data!!
            }
        }
    }


}