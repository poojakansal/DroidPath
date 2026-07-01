package com.app.droidpath.presentation.auth.login

sealed interface LoginUiEvent {
    data class ShowToast(val message:String): LoginUiEvent
}