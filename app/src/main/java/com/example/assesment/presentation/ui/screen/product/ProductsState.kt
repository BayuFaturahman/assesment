package com.example.assesment.presentation.ui.screen.product

import com.example.assesment.data.model.Product
import com.example.assesment.data.model.User
import retrofit2.Response

data class ProductsState(
    val isLoading: Boolean = false,
    val isLoadingProduct: Boolean = false,
    val isLoadingUser: Boolean = false,

    val error: String? = null,
    val listProducts: List<Product> = emptyList(),

    val listCategories: List<String> = emptyList(),
    val user: User? = null

)
