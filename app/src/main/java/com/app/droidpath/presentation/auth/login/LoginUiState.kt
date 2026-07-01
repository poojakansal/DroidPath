package com.app.droidpath.presentation.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    // True once user has tapped "Sign in" at least once.
    val submitted: Boolean = false,
    // Use this to show a loading spinner / disable the button.
    val isLoading: Boolean = false,
    // Validation errors — null means "no error" for that field.
    val emailError: String? = null,
    val passwordError: String? = null,
    // General error from the sign-in attempt itself
    // (e.g. "Invalid credentials", "Network error").
    val generalError: String? = null
) {
    /**
     * Form is valid only when both fields
     * are non-blank AND have no validation errors.
     */
    val isFormValid: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && emailError == null && passwordError == null

    val canSubmit: Boolean
        get() = isFormValid && !isLoading
}