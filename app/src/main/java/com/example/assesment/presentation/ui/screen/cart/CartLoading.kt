package com.example.assesment.presentation.ui.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer


@Composable

fun CartLoading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        for (i in 0..8) { // Adjust the range to control the number of rows
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .fillMaxWidth()
                        .shimmer()
                        .background(color = Color.Gray),
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    repeat(3) { // Adjust the repeat count to control the number of boxes in each column
                        Box(
                            modifier = Modifier
                                .height(20.dp)
                                .fillMaxWidth()
                                .shimmer()
                                .background(color = Color.Gray),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp)) // Add spacing between rows
        }
    }
}





@Preview(showBackground = true, backgroundColor = 0xFFF7F2F2)
@Composable
fun CartLoadingPreview() {
    CartLoading()
}