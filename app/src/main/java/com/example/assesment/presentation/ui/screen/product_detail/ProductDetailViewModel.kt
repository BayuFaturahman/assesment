package com.example.assesment.presentation.ui.screen.product_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assesment.data.model.CartProduct
import com.example.assesment.data.model.CartProductBody
import com.example.assesment.data.repository.ProductRepository
import com.example.assesment.domain.GetProductDetailUseCase
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val getProductDetailUseCase: GetProductDetailUseCase

    ): ViewModel() {
    private val _productDetailState = mutableStateOf(ProductDetailState())
    val productDetailState: State<ProductDetailState> get() = _productDetailState



    fun addToCartProduct(cartProduct : CartProductBody) {
        viewModelScope.launch {
            _productDetailState.value = _productDetailState.value.copy(isLoading = true)

            try {
            val result = productRepository.addToCartProduct(cartProduct)
                _productDetailState.value = _productDetailState.value.copy(
                    isLoading = false,
                    cartProduct = result
                )
            } catch (e: Exception) {
                _productDetailState.value = _productDetailState.value.copy(error = e.message)
            }
        }
    }


     fun getProductDetail(id:String) {
        viewModelScope.launch {
            _productDetailState.value = _productDetailState.value.copy(isLoadingDetailProduct = true)
            try {
                _productDetailState.value = _productDetailState.value.copy(
                    isLoadingDetailProduct = false,
                     product  = getProductDetailUseCase(id = id)
                )

            } catch (e: Exception) {
                _productDetailState.value = _productDetailState.value.copy(error = e.message)
            }
        }

    }

}
