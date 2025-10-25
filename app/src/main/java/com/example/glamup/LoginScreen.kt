package com.example.glamup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSignIn: () -> Unit,
    onGoogle: () -> Unit = {},
    onFacebook: () -> Unit = {},
    onForgot: () -> Unit = {},
    onSignUp: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPass by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("GlamUp!", fontSize = 20.sp) }) }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))
            Text("Welcome to GlamUp!", style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
            Spacer(Modifier.height(6.dp))
            Text(
                "Find beauty services near you —\nnails, lashes, makeup",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = { showPass = !showPass }) {
                        Text(if (showPass) "HIDE" else "SHOW")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
            )

            Spacer(Modifier.height(6.dp))
            TextButton(onClick = onForgot, modifier = Modifier.align(Alignment.End)) {
                Text("Forgot password?")
            }

            Spacer(Modifier.height(4.dp))
            Button(
                onClick = onSignIn, // ✅ just navigate, no validation
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) { Text("Sign In") }

            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Divider(Modifier.weight(1f))
                Text("  or  ", style = MaterialTheme.typography.bodySmall)
                Divider(Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))

            OutlinedButton(onClick = onGoogle, modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Text("Continue with Google")
            }
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick = onFacebook, modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Text("Continue with Facebook")
            }

            Spacer(Modifier.height(16.dp))
            TextButton(onClick = onSignUp) { Text("Don't have an account? Sign up") }
        }
    }
}
