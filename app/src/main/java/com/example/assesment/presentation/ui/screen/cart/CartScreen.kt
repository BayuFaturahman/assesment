package com.example.assesment.presentation.ui.screen.cart

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.assesment.R
import com.example.assesment.data.model.CartProduct
import com.example.assesment.data.model.CartProductBody
import com.example.assesment.data.model.Product
import com.example.assesment.data.model.ProductItem
import com.example.assesment.presentation.ui.component.BaseUI
import com.example.assesment.presentation.ui.component.DialogUi
import com.example.assesment.presentation.ui.screen.product.convert
import com.example.assesment.presentation.ui.theme.light1
import com.example.assesment.presentation.ui.theme.primary
import com.example.assesment.presentation.ui.theme.primaryDark
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(
    navHostController: NavHostController,
    userId: String,
    cartViewModel: CartViewModel = koinViewModel()
) {
    val state by remember { cartViewModel.cartState }
    val listState = rememberLazyListState()
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy | hh:mma")
    val outputFormatterSaveDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var isDialogDelete by remember { mutableStateOf(false) }
    var idShowDialogSuccess by remember { mutableStateOf(true) }

    var listProduct by remember { mutableStateOf(emptyList<Product>()) }
    var sumAmount by remember { mutableStateOf(0.0) }


    var idProduct by remember { mutableStateOf("") }



    LaunchedEffect(Unit) {
        cartViewModel.getCartProductByUser(userId)
    }

    if (isDialogDelete) {
        DialogUi(
            dialogTitle = "Confirmation",
            dialogSubTitle = "Are you sure to delete the product in cart?",
            onConfirmation = {
                isDialogDelete =false
                cartViewModel.deleteProductInCart(idProduct)


            },
            isDialogSingel = false,
            onDismissRequest = {
                isDialogDelete = false

            })

    }
    if (state.isLoadingDelete) {
        Dialog(
            onDismissRequest = {
                idShowDialogSuccess = false
            },

            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(light1, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }
    }

    if ( state.isSuccess == true && idShowDialogSuccess) {
        DialogUi(
            dialogTitle = "Success",
            dialogSubTitle = "Yeay, The product is delete succesfully",
            onConfirmation = {
                idShowDialogSuccess = false

            },
            isDialogSingel = true,
            onDismissRequest = {

                idShowDialogSuccess = false


            })
    }





    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BaseUI(
                navController = navHostController,
                title = "Cart",
                content = {
                    if (state.isLoading) {
                        CartLoading()
                    } else {

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            state = listState
                        ) {

                            if (state.listCartProduct.isEmpty()) {
                                item {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "No Products in Cart",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            } else {
                                itemsIndexed(state.listCartProduct) { index, product ->
                                    listProduct = state.listProductCart
                                    Box {

                                        Card(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .shadow(
                                                    elevation = 10.dp,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .fillMaxWidth()
                                                .clickable {
                                                },
                                            colors = CardDefaults.cardColors(
                                                containerColor = light1,
                                            ), shape = RoundedCornerShape(8.dp)
                                        ) {

                                            Row(
                                                modifier = Modifier.padding(14.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
//
                                                Column {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            horizontalArrangement = Arrangement.SpaceBetween
                                                        ) {
                                                            Text(
                                                                text = outputFormatter.format(
                                                                    LocalDateTime.parse(
                                                                        product.date,
                                                                        inputFormatter
                                                                    )
                                                                ),
                                                                fontSize = 16.sp,
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                            Image(
                                                                painter = painterResource(R.drawable.ic_trash),
                                                                contentDescription = "Close",
                                                                modifier = Modifier.size(30.dp).clickable {
                                                                    idProduct = product.id
                                                                    isDialogDelete =true
                                                                }
                                                            )

                                                        }

                                                    }
                                                    Spacer(modifier = Modifier.height(20.dp))

                                                    for (productItem in product.products)

                                                    Column {

                                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                                                AsyncImage(
                                                                    model = ImageRequest.Builder(
                                                                        LocalContext.current
                                                                    )
                                                                        .data(listProduct.find { it.id.toString() == product.id }?.image)
                                                                        .build(),
                                                                    contentDescription = "icon",
                                                                    contentScale = ContentScale.Inside,
                                                                    modifier = Modifier
                                                                        .width(100.dp)
                                                                        .height(100.dp)
                                                                )

                                                            }

                                                            Spacer(modifier = Modifier.height(8.dp))


                                                            listProduct.find { it.id.toString() == product.id }
                                                                    ?.let { Text(text = it.title, fontSize = 16.sp, fontWeight = FontWeight.Medium) }


                                                            Spacer(modifier = Modifier.height(14.dp))
                                                            Row(
                                                                modifier = Modifier.fillMaxWidth(),
                                                                horizontalArrangement = Arrangement.SpaceBetween
                                                            ) {
                                                                Row {
                                                                    Image(
                                                                        painter = painterResource(R.drawable.ic_minus),
                                                                        contentDescription = "Minimize",
                                                                        modifier = Modifier
                                                                            .size(25.dp)
                                                                            .clickable {
                                                                                if (productItem.quantity != 0) {
                                                                                    val updatedProducts =
                                                                                        product.products.map { item ->
                                                                                            if (item.productId == productItem.productId) {
                                                                                                item.copy(
                                                                                                    quantity = item.quantity - 1
                                                                                                )
                                                                                            } else {
                                                                                                item
                                                                                            }
                                                                                        }
                                                                                    cartViewModel.updateToCartProduct(
                                                                                        CartProduct(
                                                                                            id = product.id,
                                                                                            userId = product.userId,
                                                                                            date = product.date,
                                                                                            products = updatedProducts,
                                                                                            productsDta = emptyList()
                                                                                        ),
                                                                                        id = product.id
                                                                                    )
                                                                                }

                                                                            }
                                                                    )
                                                                    Spacer(
                                                                        modifier = Modifier.width(
                                                                            15.dp
                                                                        )
                                                                    )
                                                                    Text(
                                                                        text = productItem.quantity.toString(),
                                                                        fontSize = 16.sp,
                                                                        fontWeight = FontWeight.Bold
                                                                    )
                                                                    Spacer(
                                                                        modifier = Modifier.width(
                                                                            15.dp
                                                                        )
                                                                    )
                                                                    Image(
                                                                        painter = painterResource(R.drawable.ic_plus),
                                                                        contentDescription = "Plus",
                                                                        modifier = Modifier
                                                                            .size(25.dp)
                                                                            .clickable {
                                                                                val updatedProducts =
                                                                                    product.products.map { item ->
                                                                                        if (item.productId == productItem.productId) {
                                                                                            item.copy(
                                                                                                quantity = item.quantity + 1
                                                                                            )
                                                                                        } else {
                                                                                            item
                                                                                        }
                                                                                    }
                                                                                cartViewModel.updateToCartProduct(
                                                                                    CartProduct(
                                                                                        id = product.id,
                                                                                        userId = product.userId,
                                                                                        date = product.date,
                                                                                        products = updatedProducts,
                                                                                        productsDta = emptyList()
                                                                                    ),
                                                                                    id = product.id
                                                                                )

                                                                            }
                                                                    )
                                                                }
                                                                Column {
                                                                    androidx.compose.material3.Text(
                                                                        text = "x ${productItem.quantity}",
                                                                        fontSize = 16.sp,
                                                                        fontWeight = FontWeight.Bold,
                                                                        color = primaryDark
                                                                    )

                                                                    Text(
                                                                        text = listProduct.find { it.id.toString() == product.id }
                                                                            ?.let {

                                                                                (it.price * productItem.quantity).convert()
                                                                            }
                                                                            .toString(),
                                                                        fontSize = 16.sp,
                                                                        fontWeight = FontWeight.Bold,
                                                                        color = primaryDark
                                                                    )
                                                                }

                                                            }
                                                            Spacer(modifier = Modifier.height(20.dp))


                                                            Divider()
                                                            Spacer(modifier = Modifier.height(20.dp))

                                                        }
//                                                    Row {
//                                                        Text(text = "Sum Amount : ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                                                        Text(text = sumAmount.convert()
//                                                            ,
//                                                            fontSize = 16.sp, fontWeight = FontWeight.Bold, color = primary)
//
//                                                    }
                                                }

                                            }
                                        }
                                    }


                                }
                            }


                        }


                    }


                })
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF7F2F2)
@Composable
fun CartScreenPreview() {
//    CartScreen(
//        NavHostController(LocalContext.current), "1",        cartViewModel = koinViewModel(),
//
//        )

}