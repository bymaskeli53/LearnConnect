package com.gundogar.learnconnect

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gundogar.learnconnect.databinding.FragmentLoginBinding
import com.gundogar.learnconnect.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val viewModel: AuthViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigateToLogin()
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            hideKeyboard()
            viewModel.signUp(email, password)
        }

        collectFlows()

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding {
        return FragmentSignUpBinding.inflate(inflater, container, false)
    }

    fun FragmentSignUpBinding.navigateToLogin() {
        tvNavigateToLogin.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.signUpFragment, true) // SignUpFragment'ı backstack'ten kaldırır
                .build()

            findNavController().navigate(
                R.id.action_signUpFragment_to_loginFragment,
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
                        navigateToMainActivity()
                    }

                    is AuthResult.Error -> {
                        Snackbar.make(
                            requireView(),
                            "Kayıt hatası: ${result.exception.message}",
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