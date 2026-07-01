package com.app.droidpath.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.app.droidpath.ui.theme.CardBg
import com.app.droidpath.ui.theme.CyanStart
import com.app.droidpath.ui.theme.TextMuted

@Composable
fun DroidBottomNavBar(
    backStackEntry: NavBackStackEntry?,
    modifier: Modifier = Modifier,
    onItemClick: (Routes) -> Unit
) {
    val currentDestination = backStackEntry?.destination
    NavigationBar(
        modifier = modifier,
        containerColor = CardBg,
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = currentDestination
                ?.hierarchy
                ?.any { destination -> destination.hasRoute(item.routes::class) } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item.routes) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.filledIcon else item.outlinedIcon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CyanStart,
                    selectedTextColor = CyanStart,
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted,
                    indicatorColor = CardBg
                )
            )
        }

    }
}

/**
 * Bottom nav item metadata — pairs a Routes destination with its
 * display label and icons.
 */
data class BottomNavItem(
    val routes: Routes,
    val label: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        routes = Routes.Home,
        label = "Home",
        filledIcon = Icons.Filled.Home,
        outlinedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        routes = Routes.Learn,
        label = "Learn",
        filledIcon = Icons.AutoMirrored.Filled.MenuBook,
        outlinedIcon = Icons.AutoMirrored.Outlined.MenuBook
    )
)