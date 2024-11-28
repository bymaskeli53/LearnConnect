package com.gundogar.learnconnect.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gundogar.learnconnect.util.AuthResult
import com.gundogar.learnconnect.ui.viewmodel.AuthViewModel
import com.gundogar.learnconnect.ui.activity.MainActivity
import com.gundogar.learnconnect.R
import com.gundogar.learnconnect.databinding.FragmentLoginBinding
import com.gundogar.learnconnect.util.hideKeyboard
import com.gundogar.learnconnect.util.launchAndRepeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint


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
                       // findNavController().navigate(R.id.action_loginFragment_to_nav_graph_main)
                        //activity?.finish()
                        navigateToMainActivity()
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

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        requireActivity().finish()
        startActivity(intent)

    }


}