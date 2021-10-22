package com.example.androidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtdd.users.ui.UsersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UsersFragment())
                .commitNow()
    }
}