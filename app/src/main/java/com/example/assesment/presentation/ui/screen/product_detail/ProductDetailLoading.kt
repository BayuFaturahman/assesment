package com.example.assesment.presentation.ui.screen.product_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
fun ProductDetailLoading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
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


@Preview(showBackground = true, backgroundColor = 0xFFF7F2F2)
@Composable
fun ProductsLoadingPreview() {
    ProductDetailLoading()
}