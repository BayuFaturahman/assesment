package com.example.assesment.presentation.ui.screen.cart

import com.example.assesment.data.model.CartProduct
import com.example.assesment.data.model.Product

data class CartState(
    val isLoading:Boolean=false,
    val isLoadingDelete:Boolean=false,

    val isLoadingUpdateCart:Boolean=false,

    val error: String? = null,
    val cartProduct: Result<CartProduct>? =null,
    val listCartProduct: List<CartProduct> = emptyList(),
    val listProductCart: List<Product> =emptyList(),

    val isSuccess: Boolean? = null,


    )