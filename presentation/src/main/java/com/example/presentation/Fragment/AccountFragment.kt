package com.example.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.databinding.FragmentAccountBinding
import com.example.presentation.viewModel.TodoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCreateAccount.setOnClickListener {
            viewModel.generateKeyPair()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.accounts.collectLatest { accounts ->
                if (accounts.isNotEmpty()) {
                    binding.accountsList.visibility = View.VISIBLE
                    binding.noAccountsText.visibility = View.GONE
                    // Update account list UI
                    // TODO: Add RecyclerView adapter for accounts
                } else {
                    binding.accountsList.visibility = View.GONE
                    binding.noAccountsText.visibility = View.VISIBLE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collectLatest { error ->
                error?.let {
                    Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentSession.collectLatest { session ->
                session?.let {
                    // Navigate to TodoListFragment when session is created
                    findNavController().navigate(R.id.action_accountFragment_to_todoListFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 