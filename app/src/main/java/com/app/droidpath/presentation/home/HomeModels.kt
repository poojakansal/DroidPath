package com.app.droidpath.presentation.home

import com.app.droidpath.utils.Difficulty


/**
 * Data model for the "Continue Learning" card.
 * Represents the user's current in-progress course/lesson.
 */
data class CurrentLesson(
    val courseTitle: String,
    val lessonTitle: String,
    val progressPercent: Int,       // 0-100
    val courseIconRes: Int? = null  // drawable res id — null = use placeholder
)

/**
 * Data model for "Today's Challenge" card.
 */
data class DailyChallenge(
    val title: String,
    val tag: String,
    val difficulty: Difficulty,
    val streakDays: Int
)

/**
 * A quick topic filter chip — horizontal scrollable row
 */
data class QuickTopic(
    val label: String
)

/**
 * A saved lesson item
 */
data class SavedLesson(
    val id: String,
    val courseTitle: String,
    val lessonTitle: String,
    val iconRes: Int? = null
)

/**
 * A "What's New" course card
 */
data class NewCourse(
    val id: String,
    val title: String,
    val description: String,
    val iconRes: Int? = null
)