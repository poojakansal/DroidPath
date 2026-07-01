package com.app.droidpath.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Routes{

    @Serializable
    data object AuthNavGraph : Routes
    @Serializable
    data object DashboardNavGraph : Routes
    @Serializable
    data object Login : Routes

    @Serializable
    data object Signup : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data object Learn : Routes

    @Serializable
    data object Profile : Routes
}