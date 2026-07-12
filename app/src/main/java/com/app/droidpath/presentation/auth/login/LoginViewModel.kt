package com.app.droidpath.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.droidpath.data.repository.AuthRepository
import com.app.droidpath.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent: SharedFlow<LoginUiEvent> = _uiEvent.asSharedFlow()

    fun onEmailChange(value: String) {
        _uiState.update { current ->
            current.copy(
                email = value,
                emailError = if (current.submitted) ValidationUtils.validateEmail(value) else null
            )
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { current ->
            current.copy(
                password = value,
                passwordError = if (current.submitted) ValidationUtils.validatePassword(value) else null
            )
        }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { current ->
            current.copy(passwordVisible = !current.passwordVisible)
        }
    }

    fun onSignInClick() {
        val current = _uiState.value
        val emailError = ValidationUtils.validateEmail(current.email)
        val passwordError = ValidationUtils.validatePassword(current.password)

        _uiState.update {
            it.copy(
                submitted = true,
                emailError = emailError,
                passwordError = passwordError,
                generalError = null
            )
        }

        if (emailError != null || passwordError != null) {
            viewModelScope.launch {
                _uiEvent.emit(LoginUiEvent.ShowToast("Please fix the errors above"))
            }
            return
        }
        performSignIn()
    }

    private fun performSignIn() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.login(_uiState.value.email, _uiState.value.password).onSuccess {
                _uiEvent.emit(LoginUiEvent.NavigateToDashboard)
            }.onFailure { error ->
                _uiEvent.emit(LoginUiEvent.ShowToast(error.message ?: "Login failed"))
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    /**
     * Call this if you want to manually clear a general error
     * (e.g. user dismisses an error snackbar).
     */
    fun onGeneralErrorShown() {
        _uiState.update { it.copy(generalError = null) }
    }
}