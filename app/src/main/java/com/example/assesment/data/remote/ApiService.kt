package com.example.assesment.data.remote

import com.example.assesment.data.model.CartProduct
import com.example.assesment.data.model.CartProductBody
import com.example.assesment.data.model.Product
import com.example.assesment.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class LoginRequest(val username: String, val password: String)
data class AddToCartRequest(val cartProduct: CartProductBody
)

data class UpdateToCartRequest(val cartProduct: CartProduct
)



data class CartItem(val cartProduct: CartProduct)

data class LoginResponse(val token: String)

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProductsDetail(@Path("id") id:String): Product


    @GET("products/categories")
    suspend fun getCategories(): List<String>


    @GET("products/category/{categories}")
    suspend fun getProductByCategories(@Path("categories") categories: String):List<Product>


    @POST("auth/login")
    suspend fun login(@Body requestBody: LoginRequest): Response<LoginResponse>

    @GET("users/{id}")
    suspend fun getUserDetail(@Path("id") id:String): User


    @POST("carts")
    suspend fun addToCartProduct(@Body requestBody: AddToCartRequest): Response<CartItem>


    @GET("carts/user/{userId}")
    suspend fun getCartByUserId(@Path("userId") id:String): List<CartProduct>

    @PUT("carts/{id}")
    suspend fun updateCart(@Body requestBody: UpdateToCartRequest,@Path("id") id:String): Response<CartItem>


    @DELETE("carts/{id}")
    suspend fun deleteProductInCart(@Path("id") id:String): Response<CartItem>



}