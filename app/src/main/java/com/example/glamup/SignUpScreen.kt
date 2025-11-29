package com.example.glamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define the colors
val GlamPink = Color(0xFFE05297)
val GlamLightPink = Color(0xFFFFD1E6)
// CHANGE THESE TO MATCH YOUR XML EXACTLY
val GradientStart = Color(0xFFFF1F88) // Replace with your XML startColor
val GradientEnd = Color(0xFFF875BF)   // Replace with your XML endColor



// Create a data class to hold sign up info
data class SignUpData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val isBeautyPro: Boolean
)

@Composable
fun SignUpScreen(
    onNavigateToLogin: () -> Unit,
    // Update callback to accept data
    onSignUpClicked: (SignUpData) -> Unit
) {
    // State variables for inputs
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Checkbox logic
    var isClient by remember { mutableStateOf(false) }
    var isBeautyPro by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // --- LOGO SECTION ---
            Text(
                text = "GlamUp!",
                fontFamily = FontFamily.Cursive,
                fontSize = 64.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Find Your Sparkle",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- HEADER ---
            Text(
                text = "Register Your Account",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- FORM FIELDS ---
            GlamInputLabel("First Name")
            GlamInput(value = firstName, onValueChange = { firstName = it })

            GlamInputLabel("Last Name")
            GlamInput(value = lastName, onValueChange = { lastName = it })

            GlamInputLabel("Email")
            GlamInput(value = email, onValueChange = { email = it }, keyboardType = KeyboardType.Email)

            GlamInputLabel("Password")
            GlamInput(value = password, onValueChange = { password = it }, isPassword = true)

            GlamInputLabel("Confirm Password")
            GlamInput(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                isPassword = true,
                imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- CHECKBOXES ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isClient,
                    onCheckedChange = {
                        isClient = it
                        if(it) isBeautyPro = false
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.White,
                        checkedColor = Color.White,
                        checkmarkColor = GlamPink
                    )
                )
                Text(text = "Client?", color = Color.White, fontSize = 16.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isBeautyPro,
                    onCheckedChange = {
                        isBeautyPro = it
                        if(it) isClient = false
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.White,
                        checkedColor = Color.White,
                        checkmarkColor = GlamPink
                    )
                )
                Text(text = "BeautyPro?", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- SIGN UP BUTTON ---
            Button(
                onClick = {
                    // Validation logic before sending
                    if (password == confirmPassword && email.isNotEmpty()) {
                        onSignUpClicked(
                            SignUpData(firstName, lastName, email, password, isBeautyPro)
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = GlamLightPink),
                shape = RoundedCornerShape(50),
                modifier = Modifier.height(50.dp).width(200.dp)
            ) {
                Text("SignUp!", color = Color.DarkGray, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            // --- FOOTER ---
            Spacer(modifier = Modifier.height(32.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Already have an account?", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onNavigateToLogin,
                    colors = ButtonDefaults.buttonColors(containerColor = GlamLightPink),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("SignIn!", color = Color.DarkGray, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// ... Keep GlamInputLabel and GlamInput helper functions same as before ...
@Composable
fun GlamInputLabel(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp, top = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlamInput(
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Value", color = Color.LightGray) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = GlamPink,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        singleLine = true
    )
}
