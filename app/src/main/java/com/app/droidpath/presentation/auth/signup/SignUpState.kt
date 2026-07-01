package com.app.droidpath.presentation.auth.signup

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val generalError: String? = null,
    val submitted: Boolean = false
) {
    val isFormValid: Boolean
        get() = name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && emailError == null && passwordError == null
}
