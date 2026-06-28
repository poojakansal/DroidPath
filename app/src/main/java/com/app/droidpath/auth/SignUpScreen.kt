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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
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
fun SignUpScreen(
    onCreateAccountClick: (name: String, email: String, password: String) -> Unit = { _, _, _ -> },
    onSignInClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var submitted by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    // Live errors — null until first submission attempt
    val liveNameError = if (submitted) ValidationUtils.validateName(name) else null
    val liveEmailError = if (submitted) ValidationUtils.validateEmail(email) else null
    val livePasswordError = if (submitted) ValidationUtils.validatePassword(password) else null

    val isFormValid = liveNameError == null && liveEmailError == null
            && livePasswordError == null
            && name.isNotBlank() && email.isNotBlank() && password.isNotBlank()

    fun attemptSignUp() {
        focusManager.clearFocus()
        submitted = true
        val nErr = ValidationUtils.validateName(name)
        val eErr = ValidationUtils.validateEmail(email)
        val pErr = ValidationUtils.validatePassword(password)
        if (nErr == null && eErr == null && pErr == null) {
            Toast.makeText(context, "Creating your account...", Toast.LENGTH_SHORT).show()
            onCreateAccountClick(name, email, password)
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
                .verticalScroll(rememberScrollState())  // safe on small screens
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(48.dp))

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
                        text = "Create your account",
                        style = TextStyle(
                            color = TextPrimary,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "// Track XP, unlock badges, build the streak.",
                        style = TextStyle(
                            color = TextSecondary,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    )

                    Spacer(Modifier.height(24.dp))

                    // ── NAME ──────────────────────────────────────────────
                    FieldLabel(text = "NAME")
                    Spacer(Modifier.height(6.dp))
                    DroidPathTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = "Pooja",
                        isError = liveNameError != null,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = if (liveNameError != null) ErrorRed else IconTint,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    InlineError(message = liveNameError)

                    Spacer(Modifier.height(14.dp))

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
                        placeholder = "6+ characters",
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
                            onDone = { attemptSignUp() }
                        )
                    )
                    InlineError(message = livePasswordError)

                    Spacer(Modifier.height(24.dp))

                    // ── Create account button ─────────────────────────────
                    GradientButton(
                        text = "Create account  →",
                        onClick = { attemptSignUp() },
                        isFormValid = isFormValid,
                        submitted = submitted
                    )

                    Spacer(Modifier.height(20.dp))

                    // ── Sign in link ──────────────────────────────────────
                    val linkText = buildAnnotatedString {
                        withStyle(SpanStyle(color = TextSecondary, fontSize = 13.sp)) {
                            append("Already have an account? ")
                        }
                        withStyle(
                            SpanStyle(
                                color = TextCode,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append("Sign in")
                        }
                    }
                    TextButton(
                        onClick = onSignInClick,
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
fun SignUpScreen_Preview() {
    MaterialTheme {
        SignUpScreen()
    }
}
