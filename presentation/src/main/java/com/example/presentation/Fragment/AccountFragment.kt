package com.example.presentation.fragment

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.R
import com.example.presentation.adapter.AccountAdapter
import com.example.presentation.databinding.DialogErrorBinding
import com.example.presentation.databinding.DialogLoadingBinding
import com.example.presentation.databinding.FragmentAccountBinding
import com.example.presentation.viewModel.TodoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by viewModel()
    private var loadingDialog: Dialog? = null
    private var errorDialog: Dialog? = null
    private lateinit var accountAdapter: AccountAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        accountAdapter = AccountAdapter { account ->
            viewModel.createSession(account)
        }
        
        binding.accountsList.apply {
            adapter = accountAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupClickListeners() {
        binding.buttonCreateAccount.setOnClickListener {
            showLoading("Generating new account...")
            viewModel.generateKeyPair()
        }

        binding.todoListButton.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_todoListFragment)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.accounts.collectLatest { accounts ->
                hideLoading()
                if (accounts.isNotEmpty()) {
                    binding.accountsList.visibility = View.VISIBLE
                    binding.noAccountsText.visibility = View.GONE
                    accountAdapter.submitList(accounts)
                } else {
                    binding.accountsList.visibility = View.GONE
                    binding.noAccountsText.visibility = View.VISIBLE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collectLatest { error ->
                hideLoading()
                error?.let {
                    showError(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentSession.collectLatest { session ->
                session?.let {
                    hideLoading()
                    findNavController().navigate(R.id.action_accountFragment_to_todoListFragment)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loading.collectLatest { isLoading ->
                if (isLoading) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }
    }

    private fun showLoading(message: String = "Please wait...") {
        if (loadingDialog?.isShowing == true) return
        
        val dialogBinding = DialogLoadingBinding.inflate(layoutInflater)
        dialogBinding.loadingMessage.text = message
        
        loadingDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()
            .apply { show() }
    }

    private fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun showError(message: String) {
        if (errorDialog?.isShowing == true) return
        
        val dialogBinding = DialogErrorBinding.inflate(layoutInflater)
        dialogBinding.errorMessage.text = message
        dialogBinding.retryButton.setOnClickListener {
            errorDialog?.dismiss()
            viewModel.retry()
        }
        
        errorDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()
            .apply { show() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingDialog?.dismiss()
        errorDialog?.dismiss()
        _binding = null
    }
} 