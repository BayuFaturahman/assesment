package com.example.assesment.presentation.ui.screen.login

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    val loginViewModel: LoginViewModel = koinViewModel()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginResult by loginViewModel.loginResult.observeAsState()
    var isError by remember { mutableStateOf(false) }
    var msgError by remember { mutableStateOf("") }
    val context = LocalContext.current



    if (isError) {
        Text(
            text = msgError,
            color = MaterialTheme.colors.error
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                loginViewModel.login(username, password, context = context)

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }


    DisposableEffect(key1 = loginResult) {
        loginResult?.let { result ->
            when {
                result.isSuccess -> {
                    Log.d("cekk", "login call")
                    navController.navigate("product_screen")
                }

                result.isFailure -> {
                    isError = true
                    msgError =
                        result.exceptionOrNull()?.message ?: "Login failed. Please try again."

                }
            }
        }

        onDispose {}
    }
}