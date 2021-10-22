package com.example.androidtdd.users.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtdd.R
import com.example.androidtdd.users.presentation.UsersState
import com.example.androidtdd.users.presentation.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.users_fragment) {
    private val viewModel: UsersViewModel by viewModels()

    private val usersAdapter: UsersAdapter by lazy(LazyThreadSafetyMode.NONE) {
        UsersAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSearchView(view)
        setUpRecyclerView(view)

        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect(::handleState)
        }
    }

    private fun setUpSearchView(view: View) {
        val searchView = view.findViewById<EditText>(R.id.userSearch)
        searchView.doOnTextChanged { query, _, _, _ ->
            if (query != null) viewModel.search(query.toString())
        }
    }

    private fun setUpRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.usersRecyclerView)
        recyclerView.adapter = usersAdapter
    }


    private fun handleState(state: UsersState) = when (state) {
        UsersState.Loading -> Unit
        is UsersState.Users -> usersAdapter.users = state.users
    }
}