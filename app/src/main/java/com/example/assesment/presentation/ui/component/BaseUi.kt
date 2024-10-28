package com.example.assesment.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.assesment.R
import com.example.assesment.presentation.ui.theme.light1
import com.example.assesment.presentation.ui.theme.primary
import com.example.assesment.presentation.ui.theme.primary6
import com.example.assesment.presentation.ui.theme.textSemiBold18px
import com.example.assesment.presentation.ui.theme.white

@Composable
fun BaseUI(
    content: @Composable () -> Unit,
    navController: NavHostController,
    title: String = "",
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = primary)
            .padding(16.dp)
    ) {
        BaseHeader(title = title){
            navController.popBackStack()
        }
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
fun BaseHeader(title: String,onBack: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .width(56.dp)
                .height(56.dp)
                .clip(RoundedCornerShape(100.dp))
                .clickable {
                    onBack()
                }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title, style = textSemiBold18px.copy(color = light1)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFCFC)
@Composable
fun BaseUIPreview() {
    BaseUI(
        content = {
            Text(text = "Hello")
        },
        navController = NavHostController(LocalContext.current),
        title = "Hello"
    )

}