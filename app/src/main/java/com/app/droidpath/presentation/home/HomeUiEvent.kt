package com.app.droidpath.presentation.home

sealed interface HomeUiEvent {
    data class NavigateToLesson(val lessonId: String) : HomeUiEvent
    data class NavigateToChallenge(val challengeId: String) : HomeUiEvent
    data class NavigateToTopic(val topic: String) : HomeUiEvent
    data class NavigateToCourse(val courseId: String) : HomeUiEvent
    data class ShowToast(val message: String) : HomeUiEvent
}
