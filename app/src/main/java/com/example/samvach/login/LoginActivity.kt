// Kripiya Code likhteh waqt savdhani barteh ek bug project ko bht.... bht.... crash karwasakti h
package com.example.samvach.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.samvach.R
import com.example.samvach.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}