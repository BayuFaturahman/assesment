package com.example.assesment.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.assesment.R
import com.example.assesment.data.model.User
import com.example.assesment.presentation.ui.theme.black
import com.example.assesment.presentation.ui.theme.i
import com.example.assesment.presentation.ui.theme.light1
import com.example.assesment.presentation.ui.theme.m
import com.example.assesment.presentation.ui.theme.primary
import com.example.assesment.presentation.ui.theme.primary6
import com.example.assesment.presentation.ui.theme.textSemiBold18px
import com.example.assesment.presentation.ui.theme.white

@Composable
fun BaseHomeUI(
    content: @Composable () -> Unit,
    onLogout: () -> Unit,
    navController: NavHostController,
    user: User,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = primary)
            .padding(16.dp)
    ) {
        BaseHomeHeader(user = user, onLogout = onLogout)
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = light1)
        ) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                        content()
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}



@Composable
fun BaseHomeHeader(user: User,onLogout: () -> Unit) {

    var isDialog by remember { mutableStateOf(false) }

    if(isDialog){
        AlertDialog(
            containerColor = light1,
            modifier = Modifier.fillMaxWidth(0.92f),
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = true,
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            ),
            shape = RoundedCornerShape(20.dp),
            onDismissRequest = {

            },
            confirmButton = {
                TextButton(onClick = {
                    isDialog = false
                    onLogout()
                }) {
                    Text(text = "Logout", fontSize = 16.sp ,color = i)
                }
            },
            dismissButton = {

                TextButton(onClick = {
                    isDialog = false
                }) {
                    Text(text = "Back", fontSize = 16.sp ,color = primary)
                }

            },
            title = {
                Text(text = "Profile", fontSize = 18.sp,color = primary, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            },

            text = {
                Column  {
                    Text(text = "Name :",color = black, fontSize = 14.sp)
                    Text(text = user.name.firstname,color = black, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Username :",color = black, fontSize = 14.sp)
                    Text(text = user.username,color = black, fontSize = 14.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Email :",color = black, fontSize = 14.sp)
                    Text(text = user.email,color = black, fontSize = 14.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Phone :",color = black, fontSize = 14.sp)
                    Text(text = user.phone,color = black, fontSize = 14.sp, fontWeight = FontWeight.Bold)


                }
            })

    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
            isDialog = true

        }){
            Image(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = "Profile",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Hello, ${user.name.firstname}", style = textSemiBold18px.copy(color = light1)
            )
        }






    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFCFC)
@Composable
fun BaseHomeUIPreview() {
//    BaseHomeUI(
//        content = {
//            Text(text = "Hello")
//        },
//        navController = NavHostController(LocalContext.current),
//        name = "Bayu Faturahman",
//        onLogout = {}
//    )

}