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
import com.example.presentation.viewModel.AccountCreationState
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

        // Only show the create account button initially
        binding.accountsList.visibility = View.GONE
        binding.noAccountsText.visibility = View.VISIBLE

        binding.buttonCreateAccount.setOnClickListener {
            binding.buttonCreateAccount.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE
            viewModel.generateKeyPair()
        }

        // Only observe account creation result
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.accountCreationState.collectLatest { state ->
                when (state) {
                    is AccountCreationState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        // Navigate to TodoListFragment when account is created
                        findNavController().navigate(R.id.action_accountFragment_to_todoListFragment)
                    }
                    is AccountCreationState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonCreateAccount.isEnabled = true
                        Snackbar.make(view, state.message, Snackbar.LENGTH_LONG).show()
                    }
                    is AccountCreationState.Initial -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonCreateAccount.isEnabled = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 