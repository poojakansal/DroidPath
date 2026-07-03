package com.app.droidpath.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.droidpath.ui.theme.CardStroke
import com.app.droidpath.ui.theme.TextSecondary

/**
 * Generic category/tag chip
 */
@Composable
fun ChipTag(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, CardStroke, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                color = TextSecondary,
                fontSize = 12.sp
            )
        )
    }
}

/**
 * Difficulty chip — color-coded by [Difficulty] enum.
 * - Adding a new difficulty level (e.g. EXPERT) requires change
 *   in only one place: the Difficulty enum
 */
@Composable
fun DifficultyChip(difficulty: Difficulty) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(difficulty.bgColor)
            .padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Text(
            text = difficulty.label,
            style = TextStyle(
                color = difficulty.textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

// ─── Previews ─────────────────────────────────────────────────────────────────
@Preview(showBackground = true, backgroundColor = 0xFF0D1117)
@Composable
fun ChipTag_Preview() {
    ChipTag(label = "Easy")
}

@Preview(showBackground = true, backgroundColor = 0xFF0D1117)
@Composable
fun DifficultyChip_Preview() {
    DifficultyChip(difficulty = Difficulty.EASY)
}
