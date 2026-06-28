package com.app.droidpath.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.droidpath.R
import com.app.droidpath.ui.theme.BgDeep
import com.app.droidpath.ui.theme.CardBg
import com.app.droidpath.ui.theme.CardStroke
import com.app.droidpath.ui.theme.ErrorRed
import com.app.droidpath.ui.theme.IconTint
import com.app.droidpath.ui.theme.TextCode
import com.app.droidpath.ui.theme.TextPrimary
import com.app.droidpath.ui.theme.TextSecondary
import com.app.droidpath.utils.ValidationUtils

@Composable
fun LoginScreen(
    onSignInClick: (email: String, password: String) -> Unit = { _, _ -> },
    onCreateAccountClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var submitted by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val liveEmailError = if (submitted) ValidationUtils.validateEmail(email) else null
    val livePasswordError = if (submitted) ValidationUtils.validatePassword(password) else null

    val isFormValid = liveEmailError == null && livePasswordError == null
            && email.isNotBlank() && password.isNotBlank()

    fun attemptSignIn() {
        focusManager.clearFocus()
        submitted = true
        val eErr = ValidationUtils.validateEmail(email)
        val pErr = ValidationUtils.validatePassword(password)
        if (eErr == null && pErr == null) {
            Toast.makeText(context, "Signing in...", Toast.LENGTH_SHORT).show()
            onSignInClick(email, password)
        } else {
            Toast.makeText(context, "Please fix the errors above", Toast.LENGTH_SHORT).show()
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Logo ──────────────────────────────────────────────────────
            LogoHeader()

            Spacer(Modifier.height(28.dp))

            // ── Card ──────────────────────────────────────────────────────
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardBg,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardStroke, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {

                    Text(
                        text = "Welcome back",
                        style = TextStyle(
                            color = TextPrimary,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Sign in to keep your streak alive 🔥",
                        style = TextStyle(
                            color = TextSecondary,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    )

                    Spacer(Modifier.height(24.dp))

                    // ── EMAIL ─────────────────────────────────────────────
                    FieldLabel(text = "EMAIL")
                    Spacer(Modifier.height(6.dp))
                    DroidPathTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "you@droidpath.dev",
                        isError = liveEmailError != null,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = if (liveEmailError != null) ErrorRed else IconTint,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    InlineError(message = liveEmailError)

                    Spacer(Modifier.height(14.dp))

                    // ── PASSWORD ──────────────────────────────────────────
                    FieldLabel(text = "PASSWORD")
                    Spacer(Modifier.height(6.dp))
                    DroidPathTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "••••••••",
                        isError = livePasswordError != null,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Password,
                                contentDescription = null,
                                tint = if (livePasswordError != null) ErrorRed else IconTint,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    contentDescription = if (passwordVisible) "Hide" else "Show",
                                    tint = IconTint,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { attemptSignIn() }
                        )
                    )
                    InlineError(message = livePasswordError)

                    Spacer(Modifier.height(24.dp))

                    // ── Sign In button ────────────────────────────────────
                    GradientButton(
                        text = "Sign in  →",
                        onClick = { attemptSignIn() },
                        isFormValid = isFormValid,
                        submitted = submitted
                    )

                    Spacer(Modifier.height(20.dp))

                    // ── Create account link ───────────────────────────────
                    val linkText = buildAnnotatedString {
                        withStyle(SpanStyle(color = TextSecondary, fontSize = 13.sp)) {
                            append("New to DroidPath? ")
                        }
                        withStyle(
                            SpanStyle(
                                color = TextCode,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append("Create an account")
                        }
                    }
                    TextButton(
                        onClick = onCreateAccountClick,
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = linkText, textAlign = TextAlign.Center)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

// ─── Preview ──────────────────────────────────────────────────────────────────
@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun LoginScreen_Preview() {
    MaterialTheme {
        LoginScreen()
    }
}
