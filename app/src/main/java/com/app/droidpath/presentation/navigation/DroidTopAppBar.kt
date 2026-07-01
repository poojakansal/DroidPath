package com.app.droidpath.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.droidpath.R
import com.app.droidpath.ui.theme.CardBg
import com.app.droidpath.ui.theme.CardStroke
import com.app.droidpath.ui.theme.CyanStart
import com.app.droidpath.ui.theme.IconTint
import com.app.droidpath.ui.theme.TextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DroidTopAppBar(
    modifier: Modifier = Modifier,
    profileImageUrl: String? = null, // pass a URL/painter later when profile pics exist
    onNotificationsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = TopAppBarDefaults.windowInsets,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "DroidPath",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "DroidPath",
                    style = TextStyle(
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.2.sp
                    )
                )
            }
        },
        actions = {
            // ── Notifications with unread dot ────────────────────────────
            Box {
                IconButton(onClick = onNotificationsClick) {
                    Icon(
                        imageVector = Icons.Default.NotificationsNone,
                        contentDescription = "Notifications",
                        tint = IconTint
                    )
                }
            }

            Spacer(Modifier.width(4.dp))

            // ── Profile avatar ────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(34.dp)
                    .clip(CircleShape)
                    .border(1.dp, CardStroke, CircleShape)
                    .background(CyanStart)
                    .let { base ->
                        // clickable avatar
                        base
                    },
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onProfileClick,
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = IconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CardBg,
            titleContentColor = TextPrimary,
            actionIconContentColor = IconTint
        )
    )
}