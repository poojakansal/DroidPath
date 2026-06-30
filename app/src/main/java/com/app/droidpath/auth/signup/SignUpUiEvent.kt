package com.app.droidpath.auth.signup

sealed class SignUpUiEvent {
    data class showToast(val message:String): SignUpUiEvent()
}