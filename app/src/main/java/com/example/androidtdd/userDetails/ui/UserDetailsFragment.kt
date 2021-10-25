package com.example.androidtdd.userDetails.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.androidtdd.R
import com.example.androidtdd.userDetails.presentation.UserDetailsState.UserDetailsState
import com.example.androidtdd.userDetails.presentation.UserDetailsViewModel
import com.example.androidtdd.users.models.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsFragment : Fragment(R.layout.user_details_fragment) {
    private val viewModel: UserDetailsViewModel by viewModels()

    private val userNameTextView by lazy(LazyThreadSafetyMode.NONE) {
        requireActivity().findViewById<TextView>(R.id.userDetailsName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.state.collect(::handleState)
        }
    }

    private fun handleState(state: UserDetailsState) = when (state) {
        UserDetailsState.Loading -> Unit
        is UserDetailsState.UserDetails -> handleUserDetails(state.user)
    }

    private fun handleUserDetails(user: User) {
        userNameTextView.text = user.name
    }
}