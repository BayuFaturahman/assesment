package com.example.assesment.presentation.ui.screen.product_detail

import com.example.assesment.data.model.CartProduct
import com.example.assesment.data.model.Product

data class ProductDetailState(
    val isLoading: Boolean = false,
    val isLoadingDetailProduct: Boolean = false,

    val error: String? = null,
    val product: Product? = null,

    val cartProduct: Result<CartProduct>? = null
    )