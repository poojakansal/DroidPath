package com.app.droidpath.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.droidpath.utils.ValidationUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SignUpUiEvent>()
    val uiEvent: SharedFlow<SignUpUiEvent> = _uiEvent.asSharedFlow()

    fun onNameChange(value: String) {
        _uiState.update {
            it.copy(
                name = value,
                nameError = if (it.submitted) ValidationUtils.validateName(value) else null
            )
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(
                email = value,
                emailError = if (it.submitted) ValidationUtils.validateEmail(value) else null
            )
        }

    }

    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(
                password = value,
                passwordError = if (it.submitted) ValidationUtils.validatePassword(value) else null
            )
        }
    }

    fun onTogglePasswordVisibility() {
        viewModelScope.launch { _uiState.update { it.copy(passwordVisible = !it.passwordVisible) } }
    }

    fun onSignupSubmitted() {
        val current = _uiState.value
        val nameError = ValidationUtils.validateName(current.name)
        val emailError = ValidationUtils.validateEmail(current.email)
        val passwordError = ValidationUtils.validatePassword(current.password)

        _uiState.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                passwordError = passwordError,
                submitted = true
            )
        }
        if (nameError != null || emailError != null || passwordError != null) {
            viewModelScope.launch { _uiEvent.emit(SignUpUiEvent.ShowToast("Please fix the errors above")) }
            return
        }
        performSignUp()
    }

    private fun performSignUp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            delay(1000)

            _uiState.update { it.copy(isLoading = false) }
            _uiEvent.emit(SignUpUiEvent.ShowToast("Account created successfully!"))
        }
    }

}