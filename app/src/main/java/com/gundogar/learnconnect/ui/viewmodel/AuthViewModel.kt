package com.gundogar.learnconnect.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gundogar.learnconnect.util.AuthResult
import com.gundogar.learnconnect.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _authResult = MutableStateFlow<AuthResult<String>?>(null)
    val authResult: StateFlow<AuthResult<String>?> get() = _authResult

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> get() = _validationError

    private val _currentUserEmail = MutableStateFlow<String?>(null)
    val currentUserEmail: StateFlow<String?> get() = _currentUserEmail

    fun signUp(email: String, password: String) {
        if (validateInput(email, password)) {
            viewModelScope.launch {
                _authResult.value = authRepository.signUp(email, password)
            }
        }
    }

    fun signIn(email: String, password: String) {
        if (validateInput(email, password)) {
            viewModelScope.launch {
                _authResult.value = authRepository.signIn(email, password)
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isBlank() || !email.contains("@") -> {
                _validationError.value = "Geçerli bir email girin."
                false
            }

            password.length < 6 -> {
                _validationError.value = "Şifre en az 6 karakter olmalı."
                false
            }

            else -> true
        }
    }

    fun getCurrentUserEmail() {
        _currentUserEmail.value = authRepository.getCurrentUserEmail()
    }
}