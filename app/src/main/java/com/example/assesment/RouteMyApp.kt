package com.example.assesment

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assesment.presentation.ui.screen.cart.CartScreen
import com.example.assesment.presentation.ui.screen.login.LoginScreen
import com.example.assesment.presentation.ui.screen.product.ProductScreen
import com.example.assesment.presentation.ui.screen.product_detail.ProductDetailScreen
import com.example.assesment.presentation.ui.theme.light1

@Composable
fun AssesmentTest(
    navController: NavHostController = rememberNavController(), routeStart: String
) {

    NavHost(
        navController = navController,
        startDestination = routeStart,
        modifier = Modifier.background(light1)
    ) {
        composable("login") {
            LoginScreen(navController)
        }

        composable("product_screen") {
            ProductScreen(navController)
        }
        composable("product_detail_screen/{productId}/{userId}") { backStackEntry ->

            val productId = backStackEntry.arguments?.getString("productId")
            val userId = backStackEntry.arguments?.getString("userId")
            if (productId != null && userId != null) {
                ProductDetailScreen(navController,productId,userId)

            }
        }

        composable("cart_screen/{userId}") { backStackEntry ->

            val userId = backStackEntry.arguments?.getString("userId")
            if (userId != null) {
                CartScreen(navController,userId)

            }
        }

    }
}