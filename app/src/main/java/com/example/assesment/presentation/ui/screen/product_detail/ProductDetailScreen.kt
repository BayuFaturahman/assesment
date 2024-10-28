package com.example.assesment.presentation.ui.screen.product_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.assesment.data.model.CartProduct
import com.example.assesment.data.model.CartProductBody
import com.example.assesment.data.model.ProductItem
import com.example.assesment.presentation.ui.component.BaseUI
import com.example.assesment.presentation.ui.component.DialogUi
import com.example.assesment.presentation.ui.screen.product.convert
import com.example.assesment.presentation.ui.theme.light1
import com.example.assesment.presentation.ui.theme.o
import com.example.assesment.presentation.ui.theme.primary
import com.example.assesment.presentation.ui.theme.primary5
import com.example.assesment.presentation.ui.theme.primaryDark
import com.example.assesment.presentation.ui.theme.white
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailScreen(
    navHostController: NavHostController,
    productId: String,
    userId: String,
    productDetailViewModel: ProductDetailViewModel = koinViewModel()
) {
    val state by remember { productDetailViewModel.productDetailState }

    var isDialogAddProduct by remember { mutableStateOf(false) }
    var idShowDialogLoading by remember { mutableStateOf(false) }
    var idShowDialogSuccess by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()


    LaunchedEffect(Unit) {
        productDetailViewModel.getProductDetail(productId);

    }

    if (state.isLoading) {
        Dialog(
            onDismissRequest = { idShowDialogLoading = false },
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

    if (state.cartProduct?.isSuccess == true && idShowDialogSuccess) {
        DialogUi(
            dialogTitle = "Success",
            dialogSubTitle = "Yeay, The product is added succesfully",
            onConfirmation = {
                idShowDialogSuccess = false
                navHostController.navigate("product_screen")

            },
            isDialogSingel = true,
            onDismissRequest = {

                idShowDialogSuccess = false
                navHostController.navigate("product_screen")


            })
    }

    if (isDialogAddProduct) {
        DialogUi(
            dialogTitle = "Confirmation",
            dialogSubTitle = "Are you sure to add the product to cart?",
            onConfirmation = {
                isDialogAddProduct = false
                productDetailViewModel.addToCartProduct(
                    cartProduct = CartProductBody(
                        userId = "1", date = "2020-02-03", products = listOf(ProductItem(1, 2))
                    )
                )
            },
            isDialogSingel = false,
            onDismissRequest = {
                isDialogAddProduct = false

            })

    }


    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BaseUI(
                navController = navHostController,
                title = "Product Detail",
                content = {

                    if (state.isLoadingDetailProduct) {
                        ProductDetailLoading()
                    } else {

                        Column (modifier = Modifier.fillMaxHeight().fillMaxWidth().verticalScroll(
                            rememberScrollState()
                        )
                            , verticalArrangement = Arrangement.SpaceBetween){

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Box(
                                    modifier = Modifier.background(primary).padding(horizontal = 16.dp, vertical = 6.dp),

                                    contentAlignment = Alignment.Center
                                ) {
                                    state.product?.category?.let {
                                        androidx.compose.material3.Text(
                                            text = it.toUpperCase(
                                                Locale.ROOT
                                            ),
                                            fontSize = 14.sp,
                                            color = white,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(horizontal = 4.dp)
                                        )
                                    }
                                }
                            }
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(state.product?.image)
                                    .build(),
                                contentDescription = "icon",
                                contentScale = ContentScale.Inside,
                                modifier = Modifier.fillMaxSize()
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Column(modifier = Modifier.fillMaxHeight().background(color = primary5)) {
                                Spacer(modifier = Modifier.height(10.dp))
                                state.product?.title?.let {
                                    Text(
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                                        text = it,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {

                                        Row(verticalAlignment = Alignment.Top) {
                                            Icon(
                                                imageVector = Icons.TwoTone.Star,
                                                contentDescription = "Star",
                                                tint = o
                                            )

                                            androidx.compose.material3.Text(
                                                modifier = Modifier.padding(top = 6.dp),
                                                text = "${state.product?.rating?.rate} (${state.product?.rating?.count} reviews)",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold
                                            )

                                        }
                                    }


                                    state.product?.price?.convert()?.let {
                                        Text(
                                            text = it,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = primaryDark
                                        )
                                    }

                                }

                                state.product?.description?.let {
                                    Text(
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                                        text = it,
                                        fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                            }

                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .height(45.dp),
                                onClick = {
                                    isDialogAddProduct = true
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = primary)
                            ) {
                                Icon(
                                    imageVector = Icons.TwoTone.Add,
                                    contentDescription = "Add",
                                    tint = white
                                )
                                Text(
                                    text = "Add to Cart",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = white
                                )

                            }
                        }

                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
//    Button( modifier = Modifier.fillMaxWidth().padding(10.dp).height(45.dp),onClick = {}, colors = ButtonDefaults.buttonColors(backgroundColor = primary)) {
//        Icon(
//            imageVector = Icons.TwoTone.Add,
//            contentDescription = "Add",
//            tint = white
//        )
//        Text(
//            text = "Add to Cart",
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold,
//            color = white
//        )
//
//    }


}