package com.example.assesment.presentation.ui.screen.cart

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assesment.data.model.CartProduct
import com.example.assesment.data.model.CartProductBody
import com.example.assesment.data.model.Product
import com.example.assesment.data.repository.ProductRepository
import com.example.assesment.domain.DeleteProductInCartUseCase
import com.example.assesment.domain.GetCartByUserUseCase
import com.example.assesment.domain.GetProductCategoriesUseCase
import com.example.assesment.domain.GetProductDetailUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class CartViewModel(
    private val productRepository: ProductRepository,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val getCartByUserUseCase: GetCartByUserUseCase,
    private val deleteProductInCartUseCase: DeleteProductInCartUseCase

) : ViewModel() {

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState

    private val _cartProducts = mutableStateOf<List<Product>?>(null)

    fun getCartProductByUser(id: String) {
        viewModelScope.launch {
            _cartState.value = _cartState.value.copy(isLoading = true)
            try {
                val listItemCurrent = getCartByUserUseCase(id).toMutableList()

                for (item in listItemCurrent) {
                    val productDetails = item.products.map { product ->
                        async { getProductDetailUseCase(product.productId.toString()) }
                    }.awaitAll()

                    _cartState.value = _cartState.value.copy(
                        listProductCart = productDetails
                    )
                }

                _cartState.value = _cartState.value.copy(
                    isLoading = false,
                    listCartProduct = listItemCurrent
                )

            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
                _cartState.value = _cartState.value.copy(error = e.message)
            }
        }
    }
    fun updateToCartProduct(cartProduct: CartProduct, id: String) {
        viewModelScope.launch {
            _cartState.value = _cartState.value.copy(isLoadingUpdateCart = true)

            try {
                val listItemCurrent = getCartByUserUseCase(id)

                val updatedList: List<CartProduct> = listItemCurrent.map { item ->
                    if (item.id == id) {
                        cartProduct
                    } else {
                        item
                    }
                }

                if (productRepository.updateToCartProduct(
                        id = id,
                        cartProduct = cartProduct
                    ).isSuccess
                ) {


                    _cartState.value = _cartState.value.copy(

                        listCartProduct = updatedList
                    )

                }
            } catch (e: Exception) {
                _cartState.value = _cartState.value.copy(error = e.message)
            }
        }
    }

    fun deleteProductInCart(id: String) {
        viewModelScope.launch {
            _cartState.value = _cartState.value.copy(isLoadingDelete = true)
            try {
                if (deleteProductInCartUseCase(id = id).isSuccessful) {

                    val updatedList = _cartState.value.listCartProduct.filter { it.id != id }

                    _cartState.value = _cartState.value.copy(
                        isLoadingDelete = false,
                        isSuccess=true,
                        listCartProduct = updatedList
                    )
                }

            } catch (e: Exception) {
                _cartState.value = _cartState.value.copy(error = e.message)
            }
        }
    }
}

