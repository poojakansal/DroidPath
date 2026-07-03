package com.app.droidpath.utils

import androidx.compose.ui.graphics.Color

/**
 * Represents challenge/lesson difficulty level.
 */
enum class Difficulty(
    val label: String,
    val textColor: Color,
    val bgColor: Color
) {
    EASY(
        label = "Easy",
        textColor = Color(0xFF3FB950),
        bgColor = Color(0xFF0D2A1A)
    ),
    MEDIUM(
        label = "Medium",
        textColor = Color(0xFFF0A500),
        bgColor = Color(0xFF2A1F0A)
    ),
    HARD(
        label = "Hard",
        textColor = Color(0xFFFF5370),
        bgColor = Color(0xFF2A0D0D)
    )
}
