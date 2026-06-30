package com.app.droidpath.auth.login

sealed interface LoginUiEvent {
    data class ShowToast(val message:String): LoginUiEvent
}