package com.example.assesment.di

import com.example.assesment.data.remote.RetrofitInstance
import com.example.assesment.data.repository.AuthRepository
import com.example.assesment.data.repository.ProductRepository
import com.example.assesment.domain.DeleteProductInCartUseCase
import com.example.assesment.domain.GetCartByUserUseCase
import com.example.assesment.domain.GetCategoriesUseCase
import com.example.assesment.domain.GetProductCategoriesUseCase
import com.example.assesment.domain.GetProductDetailUseCase
import com.example.assesment.domain.GetProductsUseCase
import com.example.assesment.domain.GetUserDetailUseCase
import com.example.assesment.presentation.ui.screen.cart.CartViewModel
import com.example.assesment.presentation.ui.screen.login.LoginViewModel
import com.example.assesment.presentation.ui.screen.product.ProductsViewModel
import com.example.assesment.presentation.ui.screen.product_detail.ProductDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    // Provide the ApiService instance
    single { RetrofitInstance.api }

    // Provide AuthRepository
    single { AuthRepository(get()) }
    single { ProductRepository(get()) }


    // Provide use cases with ApiService as a dependency
    single { GetProductsUseCase(get()) }
    single { GetCategoriesUseCase(get()) }
    single { GetProductCategoriesUseCase(get()) }
    single { GetUserDetailUseCase(get()) }
    single { GetProductDetailUseCase(get()) }
    single { GetCartByUserUseCase(get()) }
    single { DeleteProductInCartUseCase(get()) }



    // ViewModels
    viewModel { LoginViewModel(get()) }
    viewModel { ProductsViewModel(get(),get(),get(),get()) }
    viewModel { ProductDetailViewModel(get(),get()) }
    viewModel { CartViewModel(get(),get(),get(),get()) }


}