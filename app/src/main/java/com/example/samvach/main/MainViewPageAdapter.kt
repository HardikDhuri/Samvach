package com.example.samvach.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.samvach.R
import com.example.samvach.main.fragments.ChatsFragment
import com.example.samvach.main.fragments.CommunityFragment
import com.example.samvach.main.fragments.NotificationsiFragment

class MainViewPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                ChatsFragment()
            }
            1 -> {
                CommunityFragment()
            }
            else -> {
                NotificationsiFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> {
                "Chats"
            }
            1 -> {
                "Community"
            }
            else -> {
                "Notifications"
            }
        }
    }
}