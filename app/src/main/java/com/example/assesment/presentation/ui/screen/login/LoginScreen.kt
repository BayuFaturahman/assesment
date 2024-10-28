package com.example.assesment.presentation.ui.screen.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.assesment.R
import com.example.assesment.presentation.ui.component.DialogUi
import com.example.assesment.presentation.ui.theme.i
import com.example.assesment.presentation.ui.theme.light1
import com.example.assesment.presentation.ui.theme.o
import com.example.assesment.presentation.ui.theme.primary

import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,

) {

    val loginViewModel: LoginViewModel = koinViewModel()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginResult by loginViewModel.loginResult.observeAsState()
    var isError by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    var isErrorUsername by remember { mutableStateOf(false) }
    var isErrorPassword by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var msgError by remember { mutableStateOf("") }
    val context = LocalContext.current
    var errorMessageUsername by remember { mutableStateOf<String?>(null) }
    var errorMessagePassword by remember { mutableStateOf<String?>(null) }


    if (loading) {
        Dialog(
            onDismissRequest = {
            },

            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(light1, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }
    }



    if (isError) {
        DialogUi(
            dialogTitle = "Sorry!",
            dialogSubTitle = msgError.toString(),
            onConfirmation = {
                isError = false


            },
            isDialogSingel = true,
            onDismissRequest = {
                isError = false

            })

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.drawable.img_login),
            contentDescription = "Login",
            modifier = Modifier.fillMaxWidth().padding(20.dp)

        )
        Spacer(modifier = Modifier.height(20.dp))
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text(text = "Happy Shopping!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = primary, modifier = Modifier.padding(bottom = 16.dp), textAlign = TextAlign.Center)

        }


        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            isError = isErrorUsername,
            modifier = Modifier.fillMaxWidth()
        )
        if(isErrorUsername)         Text(text = errorMessageUsername.toString(), color = i,fontSize = 14.sp, modifier = Modifier.padding(bottom = 16.dp))

        Spacer(modifier = Modifier.height(14.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            isError =isErrorPassword ,
            trailingIcon = {

                Image(
                    painter = if(isPasswordVisible) { painterResource(R.drawable.ic_eye)}else {painterResource(R.drawable.ic_remove_eye)},
                    contentDescription = "Profile",
                    modifier = Modifier.size(25.dp).clickable {
                        isPasswordVisible = !isPasswordVisible
                    }
                )

                           },

            visualTransformation =if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        if(isErrorUsername)         Text(text = errorMessageUsername.toString(), color = i,fontSize = 14.sp, modifier = Modifier.padding(bottom = 16.dp))

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = primary
            ),
            onClick = {
                if (username.isEmpty() || password.isEmpty()) {
                    isErrorPassword =true
                    isErrorUsername = true
                    errorMessageUsername = "Username is not empty!"
                    errorMessagePassword = "Password is not empty!"
                }else{
                    loading = true
                    isErrorPassword =false
                    isErrorUsername = false

                    errorMessageUsername = ""
                    errorMessagePassword = ""
                    loginViewModel.login(username, password, context = context)

                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold,color = light1, modifier = Modifier.padding(vertical = 4.dp))
        }
    }


    DisposableEffect(key1 = loginResult) {
        loginResult?.let { result ->
            when {
                result.isSuccess -> {
                    loading = false

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


@Preview(showBackground = true, backgroundColor = 0xFFF7F2F2)
@Composable
fun LoginPreview() {
    LoginScreen(navController = NavHostController(LocalContext.current))

}