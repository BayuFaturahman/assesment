package com.example.assesment.data.repository

import android.util.Log
import com.example.assesment.data.model.CartProduct
import com.example.assesment.data.model.CartProductBody
import com.example.assesment.data.remote.AddToCartRequest
import com.example.assesment.data.remote.ApiService
import com.example.assesment.data.remote.UpdateToCartRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ProductRepository(private val apiService: ApiService) {


    suspend fun addToCartProduct(cartProduct: CartProductBody): Result<CartProduct> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.addToCartProduct(AddToCartRequest(cartProduct))
                if (response.isSuccessful) {
                    Result.success(response.body()?.cartProduct) as Result<CartProduct>
                } else {
                    Result.failure(Exception("Login failed with code: ${response.code()}"))
                }
            } catch (e: HttpException) {
                Result.failure(Exception("Network error: ${e.message}"))
            } catch (e: Exception) {
                Result.failure(Exception("Unexpected error: ${e.message}"))
            }
        }
    }

    suspend fun updateToCartProduct(id:String,cartProduct: CartProduct): Result<CartProduct> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.updateCart( UpdateToCartRequest(cartProduct = cartProduct
                ),id)
                Log.d("REPOSITORY :" , cartProduct.toString())
                Log.d("REPOSITORY :" , response.isSuccessful.toString())

                Log.d("REPOSITORY :" , response.errorBody().toString())
                Log.d("REPOSITORY :" , response.body().toString())

                if (response.isSuccessful) {
                    Result.success(response.body()?.cartProduct) as Result<CartProduct>
                } else {
                    Result.failure(Exception("Fetch failed with code: ${response.code()}"))
                }
            } catch (e: HttpException) {
                Result.failure(Exception("Network error: ${e.message}"))
            } catch (e: Exception) {
                Result.failure(Exception("Unexpected error: ${e.message}"))
            }
        }
    }
}