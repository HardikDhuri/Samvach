// Kripiya Code likhteh waqt savdhani barteh ek bug project ko bht.... bht.... crash karwasakti h
package com.example.samvach.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.viewpager.widget.ViewPager
import com.example.samvach.R
import com.example.samvach.databinding.ActivityMainBinding
import com.example.samvach.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = main_activity_pager
        viewPager.adapter = MainViewPageAdapter(supportFragmentManager)


        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun redirectToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}