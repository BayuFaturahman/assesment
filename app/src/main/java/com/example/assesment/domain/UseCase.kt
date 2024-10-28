package com.example.assesment.domain

import android.util.Log
import com.example.assesment.data.model.CartProduct
import com.example.assesment.data.model.Product
import com.example.assesment.data.model.User
import com.example.assesment.data.remote.ApiService
import com.example.assesment.data.remote.CartItem
import retrofit2.Response

class GetProductsUseCase(private val apiService: ApiService) {
    suspend operator fun invoke(): List<Product> {
        return apiService.getProducts()
    }
}

class GetProductDetailUseCase(private val apiService: ApiService) {
    suspend operator fun invoke(id:String): Product {
        return apiService.getProductsDetail(id =id)
    }
}

class GetUserDetailUseCase(private val apiService: ApiService) {
    suspend operator fun invoke(id:String): User {

        return apiService.getUserDetail(id =id )
    }
}

class GetCartByUserUseCase(private val apiService: ApiService) {
    suspend operator fun invoke(id:String): List<CartProduct> {

        return apiService.getCartByUserId(id=id)
    }
}

class GetCategoriesUseCase(private val apiService: ApiService) {
    suspend operator fun invoke(): List<String> {
        return apiService.getCategories()
    }
}

class GetProductCategoriesUseCase(private val apiService: ApiService) {
    suspend operator fun invoke(category: String): List<Product> {
        return apiService.getProductByCategories(category)
    }
}

    class DeleteProductInCartUseCase(private val apiService: ApiService) {
        suspend operator fun invoke(id: String): Response<CartItem> {
            return apiService.deleteProductInCart(id)
        }
}