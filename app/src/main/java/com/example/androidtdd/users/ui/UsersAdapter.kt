package com.example.androidtdd.users.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtdd.R
import com.example.androidtdd.users.models.User

class UsersAdapter(
    private val onUserClick: (Int) -> Unit,
) : RecyclerView.Adapter<UsersAdapter.UserHolder>() {

    var users: List<User> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_user_card, parent, false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) =
        holder.bind(users[position])

    override fun getItemCount(): Int = users.size

    inner class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.name)
        private val emailTextView = itemView.findViewById<TextView>(R.id.email)
        private val phoneNumberTextView = itemView.findViewById<TextView>(R.id.phoneNumber)

        fun bind(user: User) {
            nameTextView.text = user.name
            emailTextView.text = user.email
            phoneNumberTextView.text = user.phone

            itemView.setOnClickListener { onUserClick(user.id) }
        }
    }
}