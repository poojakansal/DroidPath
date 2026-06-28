package com.app.droidpath.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.droidpath.R
import com.app.droidpath.ui.theme.*

// ─────────────────────────────────────────────────────────────────────────────
//  LogoHeader — DroidPath icon + wordmark
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun LogoHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "DroidPath",
            modifier = Modifier
                .size(70.dp)
        )
        Spacer(Modifier.width(5.dp))
        Text(
            text = "DroidPath",
            style = TextStyle(
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.3.sp
            )
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  FieldLabel — uppercase small label above each field
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun FieldLabel(text: String) {
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

// ─────────────────────────────────────────────────────────────────────────────
//  InlineError — animated error pill under a field
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun InlineError(message: String?) {
    AnimatedVisibility(
        visible = message != null,
        enter   = fadeIn() + expandVertically(),
        exit    = fadeOut() + shrinkVertically()
    ) {
        if (message != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            ) {
                Text(text = "⚠", color = ErrorRed, fontSize = 12.sp)
                Spacer(Modifier.width(6.dp))
                Text(
                    text = message,
                    style = TextStyle(
                        color = ErrorRed,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    )
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  DroidPathTextField — shared styled OutlinedTextField
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun DroidPathTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        isError = isError,
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    color = TextMuted,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            )
        },
        textStyle = TextStyle(
            color = TextPrimary,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor   = FieldBg,
            unfocusedContainerColor = FieldBg,
            errorContainerColor     = FieldBg,
            focusedBorderColor      = if (isError) ErrorRed else CyanStart.copy(alpha = 0.7f),
            unfocusedBorderColor    = if (isError) ErrorRed.copy(alpha = 0.6f) else FieldStroke,
            errorBorderColor        = ErrorRed,
            cursorColor             = CyanStart,
            errorCursorColor        = ErrorRed,
            errorLeadingIconColor   = ErrorRed,
        )
    )
}

// ─────────────────────────────────────────────────────────────────────────────
//  GradientButton — cyan-to-blue CTA button
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    isFormValid: Boolean,
    submitted: Boolean,
) {
    val gradient = if (isFormValid || !submitted) buttonGradient else buttonGradientDisabled
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor   = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    letterSpacing = 0.4.sp
                )
            )
        }
    }
}
