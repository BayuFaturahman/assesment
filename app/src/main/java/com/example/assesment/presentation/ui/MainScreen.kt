package com.example.assesment.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assesment.data.model.Product
import com.example.assesment.presentation.ui.screen.product.ProductScreenPreview

@Composable
fun MainScreen() {


@Composable
fun MainScreenPreviewContent(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProductScreenPreview()
    }
}}