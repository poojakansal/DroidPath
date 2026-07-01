package com.app.droidpath.presentation.auth.signup

sealed interface SignUpUiEvent {
    data class ShowToast(val message:String): SignUpUiEvent
}