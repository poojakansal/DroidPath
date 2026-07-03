package com.app.droidpath.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.droidpath.R
import com.app.droidpath.ui.theme.*
import com.app.droidpath.utils.ChipTag
import com.app.droidpath.utils.DifficultyChip

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToLesson: (lessonId: String) -> Unit = {},
    onNavigateToChallenge: (challengeId: String) -> Unit = {},
    onNavigateToTopic: (topic: String) -> Unit = {},
    onNavigateToCourse: (courseId: String) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HomeUiEvent.NavigateToLesson    -> onNavigateToLesson(event.lessonId)
                is HomeUiEvent.NavigateToChallenge -> onNavigateToChallenge(event.challengeId)
                is HomeUiEvent.NavigateToTopic     -> onNavigateToTopic(event.topic)
                is HomeUiEvent.NavigateToCourse    -> onNavigateToCourse(event.courseId)
                is HomeUiEvent.ShowToast           -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BgDeep.copy(alpha = 0.55f))
        )

        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = CyanStart
                )
            }
            state.errorMessage != null -> {
                ErrorState(
                    message = state.errorMessage!!,
                    onRetry = viewModel::onRetry,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // ── Greeting ──────────────────────────────────────────
                    GreetingSection(
                        userName = state.userName,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    // ── Continue Learning ─────────────────────────────────
                    state.currentLesson?.let { lesson ->
                        ContinueLearningCard(
                            lesson = lesson,
                            onResumeClick = viewModel::onResumeClick,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }

                    // ── Today's Challenge ─────────────────────────────────
                    state.dailyChallenge?.let { challenge ->
                        TodaysChallengeCard(
                            challenge = challenge,
                            onSolveNowClick = viewModel::onSolveNowClick,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }

                    // ── Quick Topics ──────────────────────────────────────
                    if (state.quickTopics.isNotEmpty()) {
                        QuickTopicsSection(
                            topics = state.quickTopics,
                            onTopicClick = viewModel::onTopicClick
                        )
                    }

                    // ── Saved ─────────────────────────────────────────────
                    if (state.savedLessons.isNotEmpty()) {
                        SavedSection(
                            lessons = state.savedLessons,
                            onLessonClick = viewModel::onSavedLessonClick,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }

                    // ── What's New ────────────────────────────────────────
                    if (state.newCourses.isNotEmpty()) {
                        WhatsNewSection(
                            courses = state.newCourses,
                            onCourseClick = viewModel::onNewCourseClick,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }

                    // Bottom spacing so last card clears the bottom nav bar
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  GreetingSection
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun GreetingSection(
    userName: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Hello, $userName 👋",
            style = TextStyle(
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "// Make it work, make it right, make it fast.",
            style = TextStyle(
                color = TextSecondary,
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace
            )
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  ContinueLearningCard
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun ContinueLearningCard(
    lesson: CurrentLesson,
    onResumeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CardContainer(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "▷", color = TextSecondary, fontSize = 11.sp)
                    Spacer(Modifier.width(6.dp))
                    SectionLabel(text = "CONTINUE LEARNING")
                }
                Text(
                    text = "${lesson.progressPercent}%",
                    style = TextStyle(
                        color = CyanStart,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Spacer(Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CourseIcon(iconRes = lesson.courseIconRes, contentDescription = lesson.courseTitle)
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = lesson.courseTitle,
                        style = TextStyle(color = TextSecondary, fontSize = 12.sp)
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = lesson.lessonTitle,
                        style = TextStyle(
                            color = TextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { lesson.progressPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = CyanStart,
                        trackColor = FieldStroke,
                        strokeCap = StrokeCap.Round
                    )
                }
                Spacer(Modifier.width(12.dp))
                Button(
                    onClick = onResumeClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CyanStart,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Resume →",
                        style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  TodaysChallengeCard
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun TodaysChallengeCard(
    challenge: DailyChallenge,
    onSolveNowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CardContainer(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "✦", color = TextSecondary, fontSize = 11.sp)
                    Spacer(Modifier.width(6.dp))
                    SectionLabel(text = "TODAY'S CHALLENGE")
                }
                StreakBadge(days = challenge.streakDays)
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = challenge.title,
                style = TextStyle(
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                )
            )

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ChipTag(label = challenge.tag)
                DifficultyChip(difficulty = challenge.difficulty)
            }

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(buttonGradient),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = onSolveNowClick,
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(
                        text = "Solve Now",
                        style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  QuickTopicsSection — horizontally scrollable topic chips
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun QuickTopicsSection(
    topics: List<QuickTopic>,
    onTopicClick: (QuickTopic) -> Unit
) {
    Column {
        SectionHeader(
            text = "QUICK TOPICS",
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(10.dp))
        // LazyRow so the chip list scrolls horizontally without being clipped
        // by the parent Column's horizontal padding — edge chips are fully visible
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(topics) { topic ->
                TopicChip(
                    label = topic.label,
                    onClick = { onTopicClick(topic) }
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  SavedSection
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun SavedSection(
    lessons: List<SavedLesson>,
    onLessonClick: (SavedLesson) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "🔖", fontSize = 13.sp)
            Spacer(Modifier.width(6.dp))
            SectionHeader(text = "SAVED")
        }
        Spacer(Modifier.height(10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            lessons.forEach { lesson ->
                SavedLessonCard(
                    lesson = lesson,
                    onClick = { onLessonClick(lesson) }
                )
            }
        }
    }
}

@Composable
private fun SavedLessonCard(
    lesson: SavedLesson,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardBg)
            .border(1.dp, CardStroke, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CourseIcon(
                iconRes = lesson.iconRes,
                contentDescription = lesson.courseTitle,
                size = 44.dp
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = lesson.courseTitle,
                    style = TextStyle(color = TextSecondary, fontSize = 12.sp)
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = lesson.lessonTitle,
                    style = TextStyle(
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  WhatsNewSection
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun WhatsNewSection(
    courses: List<NewCourse>,
    onCourseClick: (NewCourse) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SectionHeader(text = "WHAT'S NEW")
        Spacer(Modifier.height(10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            courses.forEach { course ->
                NewCourseCard(
                    course = course,
                    onClick = { onCourseClick(course) }
                )
            }
        }
    }
}

@Composable
private fun NewCourseCard(
    course: NewCourse,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardBg)
            .border(1.dp, CardStroke, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CourseIcon(
                    iconRes = course.iconRes,
                    contentDescription = course.title,
                    size = 44.dp
                )
                Spacer(Modifier.width(10.dp))
                // NEW badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyanStart.copy(alpha = 0.15f))
                        .border(1.dp, CyanStart.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "NEW",
                        style = TextStyle(
                            color = CyanStart,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = course.title,
                style = TextStyle(
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = course.description,
                style = TextStyle(
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  Shared private sub-composables
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = TextStyle(
            color = TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.2.sp
        ),
        modifier = modifier
    )
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = TextStyle(
            color = TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.2.sp
        )
    )
}

@Composable
private fun CourseIcon(
    iconRes: Int?,
    contentDescription: String,
    size: androidx.compose.ui.unit.Dp = 56.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1A2332)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes ?: R.drawable.ic_launcher_foreground),
            contentDescription = contentDescription,
            modifier = Modifier.size(size * 0.7f)
        )
    }
}

@Composable
private fun TopicChip(
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, CardStroke, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                color = TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
private fun StreakBadge(days: Int) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF2A1F0A))
            .border(1.dp, Color(0xFF6B4A10), RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "🔥", fontSize = 13.sp)
            Spacer(Modifier.width(4.dp))
            Text(
                text = "$days day streak",
                style = TextStyle(
                    color = Color(0xFFF0A500),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  Error state
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = message,
            style = TextStyle(
                color = ErrorRed,
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace
            )
        )
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CyanStart)
        ) {
            Text(text = "Retry", color = Color.White)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  CardContainer — public so other screens can reuse it
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun CardContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardBg)
            .border(1.dp, CardStroke, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        content()
    }
}

// ─── Preview ──────────────────────────────────────────────────────────────────
@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun HomeScreen_Preview() {
    MaterialTheme {
        HomeScreen()
    }
}