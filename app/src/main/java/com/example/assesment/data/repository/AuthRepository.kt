package com.example.assesment.data.repository

import com.example.assesment.data.remote.ApiService
import com.example.assesment.data.remote.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class AuthRepository(private val apiService: ApiService) {
    suspend fun login(username: String, password: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(LoginRequest(username, password))
                if (response.isSuccessful) {
                    Result.success(response.body()?.token ?: "")
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
}