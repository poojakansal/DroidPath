package com.app.droidpath.utils

import android.util.Patterns

object ValidationUtils {

    fun validateName(name: String): String? = when {
        name.isBlank() -> "Name is required"
        name.length < 2 -> "Name must be at least 2 characters"
        else -> null
    }

    fun validateEmail(email: String): String? = when {
        email.isBlank() -> "Email is required"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Enter a valid email address"
        else -> null
    }

    fun validatePassword(password: String): String? = when {
        password.isBlank() -> "Password is required"
        password.length < 6 -> "Password must be at least 6 characters"
        else -> null
    }
}