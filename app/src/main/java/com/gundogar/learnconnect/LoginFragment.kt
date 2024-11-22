package com.gundogar.learnconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gundogar.learnconnect.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: AuthViewModel by viewModels()


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigateToSignUp()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            hideKeyboard()
            viewModel.signIn(email, password)
        }

        collectFlows()


    }

    fun FragmentLoginBinding.navigateToSignUp() {
        tvNavigateToSignUp.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true) // SignUpFragment'ı backstack'ten kaldırır
                .build()

            findNavController().navigate(
                R.id.action_loginFragment_to_signUpFragment,
                null,
                navOptions
            )
        }
    }

    private fun collectFlows() {

        launchAndRepeatOnLifecycle {
            viewModel.authResult.collect { result ->
                when (result) {
                    is AuthResult.Success -> {
                        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                    }

                    is AuthResult.Error -> {
                        Snackbar.make(
                            requireView(),
                            "Giriş hatası: ${result.exception.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                    null -> Unit
                }
            }
        }


        launchAndRepeatOnLifecycle {
            viewModel.validationError.collect { error ->
                error?.let {
                    Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }


}