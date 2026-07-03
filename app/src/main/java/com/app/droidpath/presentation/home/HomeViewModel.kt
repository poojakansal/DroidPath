package com.app.droidpath.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.droidpath.utils.Difficulty
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>(extraBufferCapacity = 1)
    val uiEvent: SharedFlow<HomeUiEvent> = _uiEvent.asSharedFlow()

    init {
        loadHomeData()
    }

    // ── Data loading ────────────────────────────────────────────────────────

    private fun loadHomeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // TODO: replace with real repository calls
            delay(800)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    userName = "Learner",
                    currentLesson = CurrentLesson(
                        courseTitle = "Kotlin Coroutines & Flow",
                        lessonTitle = "Suspend Functions",
                        progressPercent = 42
                    ),
                    dailyChallenge = DailyChallenge(
                        title = "Cancel a Coroutine on Scope Clear",
                        tag = "Coroutines",
                        difficulty = Difficulty.EASY,
                        streakDays = 7
                    ),
                    quickTopics = listOf(
                        QuickTopic("Kotlin"),
                        QuickTopic("Compose"),
                        QuickTopic("MVVM"),
                        QuickTopic("Hilt"),
                        QuickTopic("Room"),
                        QuickTopic("Flow"),
                        QuickTopic("Retrofit")
                    ),
                    savedLessons = listOf(
                        SavedLesson(
                            id = "compose_basics_first",
                            courseTitle = "Jetpack Compose Basics",
                            lessonTitle = "Your First Composable"
                        ),
                        SavedLesson(
                            id = "nav_compose_typesafe",
                            courseTitle = "Navigation Compose",
                            lessonTitle = "Type-Safe Routes"
                        )
                    ),
                    newCourses = listOf(
                        NewCourse(
                            id = "coroutines_flow",
                            title = "Kotlin Coroutines & Flow",
                            description = "suspend functions, scopes, StateFlow, SharedFlow, channels."
                        )
                    )
                )
            }
        }
    }

    // ── User actions ────────────────────────────────────────────────────────

    fun onResumeClick() {
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvent.NavigateToLesson("coroutines_suspend_fun"))
        }
    }

    fun onSolveNowClick() {
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvent.NavigateToChallenge("cancel_coroutine_scope"))
        }
    }

    fun onTopicClick(topic: QuickTopic) {
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvent.NavigateToTopic(topic.label))
        }
    }

    fun onSavedLessonClick(lesson: SavedLesson) {
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvent.NavigateToLesson(lesson.id))
        }
    }

    fun onNewCourseClick(course: NewCourse) {
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvent.NavigateToCourse(course.id))
        }
    }

    fun onRetry() {
        loadHomeData()
    }
}