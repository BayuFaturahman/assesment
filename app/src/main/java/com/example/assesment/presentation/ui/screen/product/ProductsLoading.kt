package com.example.assesment.presentation.ui.screen.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun ProductsLoading() {
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(20) { index ->
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .padding(
                        start = if (index % 2 == 1) 6.dp else 0.dp,
                        end = if (index % 2 == 0) 6.dp else 0.dp,
                        bottom = 16.dp
                    )
            ) {
                Box(
                    modifier = Modifier
                        .height(159.dp)
                        .fillMaxWidth()
                        .shimmer()
                        .background(color = Color.Gray),
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                        .shimmer()
                        .background(color = Color.Gray),
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                        .shimmer()
                        .background(color = Color.Gray),
                )
            }
        }
    }, modifier = Modifier.wrapContentHeight())
}


@Preview(showBackground = true, backgroundColor = 0xFFF7F2F2)
@Composable
fun ProductsLoadingPreview() {
    ProductsLoading()
}