package com.app.droidpath.presentation.auth.signup

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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.droidpath.R
import com.app.droidpath.presentation.auth.DroidPathTextField
import com.app.droidpath.presentation.auth.FieldLabel
import com.app.droidpath.presentation.auth.GradientButton
import com.app.droidpath.presentation.auth.InlineError
import com.app.droidpath.presentation.auth.LogoHeader
import com.app.droidpath.ui.theme.BgDeep
import com.app.droidpath.ui.theme.CardBg
import com.app.droidpath.ui.theme.CardStroke
import com.app.droidpath.ui.theme.ErrorRed
import com.app.droidpath.ui.theme.IconTint
import com.app.droidpath.ui.theme.TextCode
import com.app.droidpath.ui.theme.TextPrimary
import com.app.droidpath.ui.theme.TextSecondary

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    onCreateAccountClick: (name: String, email: String, password: String) -> Unit = { _, _, _ -> },
    onSignInClick: () -> Unit = {}
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event->
            when(event){
                is SignUpUiEvent.ShowToast -> Toast.makeText(context,event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun attemptSignUp() {
        focusManager.clearFocus()
        viewModel.onSignupSubmitted()
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
                        value = state.name,
                        onValueChange = viewModel::onNameChange,
                        placeholder = "Learner",
                        isError = state.nameError != null,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = if (state.nameError != null) ErrorRed else IconTint,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    InlineError(message = state.nameError)

                    Spacer(Modifier.height(14.dp))

                    // ── EMAIL ─────────────────────────────────────────────
                    FieldLabel(text = "EMAIL")
                    Spacer(Modifier.height(6.dp))
                    DroidPathTextField(
                        value = state.email,
                        onValueChange = viewModel::onEmailChange,
                        placeholder = "you@droidpath.dev",
                        isError = state.emailError != null,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = if (state.emailError != null) ErrorRed else IconTint,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    InlineError(message = state.emailError)

                    Spacer(Modifier.height(14.dp))

                    // ── PASSWORD ──────────────────────────────────────────
                    FieldLabel(text = "PASSWORD")
                    Spacer(Modifier.height(6.dp))
                    DroidPathTextField(
                        value = state.password,
                        onValueChange = viewModel::onPasswordChange,
                        placeholder = "6+ characters",
                        isError = state.passwordError != null,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Password,
                                contentDescription = null,
                                tint = if (state.passwordError != null) ErrorRed else IconTint,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = viewModel::onTogglePasswordVisibility) {
                                Icon(
                                    imageVector = if (state.passwordVisible) Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    contentDescription = if (state.passwordVisible) "Hide" else "Show",
                                    tint = IconTint,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        },
                        visualTransformation = if (state.passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { attemptSignUp() }
                        )
                    )
                    InlineError(message = state.passwordError)

                    Spacer(Modifier.height(24.dp))

                    // ── Create account button ─────────────────────────────
                    GradientButton(
                        text = "Create account  →",
                        onClick = { attemptSignUp() },
                        isFormValid = state.isFormValid,
                        submitted = state.submitted
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
