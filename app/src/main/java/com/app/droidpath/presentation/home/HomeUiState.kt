package com.app.droidpath.presentation.home

/**
 * Persistent UI state for the Home screen.
 */

data class HomeUiState(
    val userName: String = "",
    val currentLesson: CurrentLesson? = null,
    val dailyChallenge: DailyChallenge? = null,
    val quickTopics: List<QuickTopic> = emptyList(),
    val savedLessons: List<SavedLesson> = emptyList(),
    val newCourses: List<NewCourse> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
