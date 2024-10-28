package com.example.assesment.presentation.ui.screen.product

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assesment.domain.GetProductsUseCase
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.example.assesment.domain.GetCategoriesUseCase
import com.example.assesment.domain.GetProductCategoriesUseCase
import com.example.assesment.domain.GetUserDetailUseCase

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductCategoriesUseCase: GetProductCategoriesUseCase
) : ViewModel() {

    private val _productState = mutableStateOf(ProductsState())
    val productState: State<ProductsState> get() = _productState



      fun getProduct() {
            viewModelScope.launch {
                _productState.value = _productState.value.copy(isLoadingProduct = true)
                try {

                    _productState.value = _productState.value.copy(
                        isLoadingProduct = false,
                        listProducts = getProductsUseCase()
                    )
                } catch (e: Exception) {
                    _productState.value = _productState.value.copy(error = e.message)
                }
        }

    }

   private fun getUserDetail() {
        viewModelScope.launch {
            _productState.value = _productState.value.copy(isLoadingUser = true)
            try {

                _productState.value = _productState.value.copy(
                    isLoadingUser = false,
                    user = getUserDetailUseCase(id = "1")
                )
                getCategories()

            } catch (e: Exception) {
                _productState.value = _productState.value.copy(error = e.message)
            }
        }

    }


     fun getProductByCategories(category: String) {
        viewModelScope.launch {
            _productState.value = _productState.value.copy(isLoadingProduct = true)
            try {
                _productState.value = _productState.value.copy(
                    isLoadingProduct = false,
                    listProducts = getProductCategoriesUseCase(category)
                )

            } catch (e: Exception) {
                _productState.value = _productState.value.copy(error = e.message)
            }
        }

    }


    private fun getCategories() {

        viewModelScope.launch {
            _productState.value = _productState.value.copy(isLoading = true)
            try {

                val categories = getCategoriesUseCase()

                val newCategories = mutableListOf("All")
                newCategories.addAll(categories)

                _productState.value = _productState.value.copy(
                    isLoading = false,
                    listCategories = newCategories
                )
                getProduct()

            } catch (e: Exception) {
                _productState.value = _productState.value.copy(error = e.message)
            }
        }

    }

    init {
        getUserDetail()
    }


}
