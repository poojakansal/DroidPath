package com.app.droidpath.presentation.commonComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.droidpath.ui.theme.CyanStart
import com.app.droidpath.ui.theme.ErrorRed
import com.app.droidpath.ui.theme.FieldBg
import com.app.droidpath.ui.theme.FieldStroke
import com.app.droidpath.ui.theme.TextMuted
import com.app.droidpath.ui.theme.TextPrimary

// ─────────────────────────────────────────────────────────────────────────────
//  CustomTextField — shared styled OutlinedTextField
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(10.dp),
        singleLine = singleLine ?: true,
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
            focusedContainerColor = FieldBg,
            unfocusedContainerColor = FieldBg,
            errorContainerColor = FieldBg,
            focusedBorderColor = if (isError) ErrorRed else CyanStart.copy(alpha = 0.7f),
            unfocusedBorderColor = if (isError) ErrorRed.copy(alpha = 0.6f) else FieldStroke,
            errorBorderColor = ErrorRed,
            cursorColor = CyanStart,
            errorCursorColor = ErrorRed,
            errorLeadingIconColor = ErrorRed,
        )
    )
}