package com.example.androidtdd.users.ui

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtdd.users.models.User

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserHolder>() {

    var users: List<User> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = TextView(parent.context)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) =
        holder.bind(users[position])

    override fun getItemCount(): Int = users.size

    inner class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: User) {
            (itemView as TextView).text = user.name
        }
    }
}