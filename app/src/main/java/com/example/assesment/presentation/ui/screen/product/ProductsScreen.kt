package com.example.assesment.presentation.ui.screen.product

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.assesment.R
import com.example.assesment.data.model.Product
import com.example.assesment.data.model.Rating
import com.example.assesment.presentation.ui.component.BaseHomeUI
import com.example.assesment.presentation.ui.component.DialogUi
import com.example.assesment.presentation.ui.theme.black
import com.example.assesment.presentation.ui.theme.light1
import com.example.assesment.presentation.ui.theme.o
import com.example.assesment.presentation.ui.theme.primary
import com.example.assesment.presentation.ui.theme.primaryDark
import com.example.assesment.presentation.ui.theme.white
import com.example.assesment.utils.preference.PreferencesManager
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductScreen(navHostController: NavHostController,productsViewModel: ProductsViewModel = koinViewModel()) {

    val state by remember { productsViewModel.productState }
    var isDialogLogout by remember { mutableStateOf(false) }
    val preferencesManager = PreferencesManager(LocalContext.current)

    var isChoiceCategory by remember { mutableStateOf("All") }


    if (isDialogLogout) {
        DialogUi(
            dialogTitle = "Confirmation",
            dialogSubTitle = "Are you sure to Logout app ?",
            onConfirmation = {
                isDialogLogout = false
                preferencesManager.saveData(R.string.key_token.toString(),"")
                navHostController.navigate("login")

            },
            isDialogSingel = false,
            onDismissRequest = {
                isDialogLogout = false

            })

    }

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(20.dp),
                backgroundColor = primary,
                onClick = {
                    navHostController.navigate("cart_screen/{userId}".replace(oldValue = "{userId}", newValue = state.user?.id.toString()))
                }) {
                Icon(
                    imageVector = Icons.TwoTone.ShoppingCart,
                    tint = white,
                    contentDescription = "Cart"
                )
            }

        }

    ){
            innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            state.user?.let {
                BaseHomeUI (
                    user = it,
                    navController = NavHostController(LocalContext.current),
                    onLogout = {
                        isDialogLogout = true
                    },
                    content = {
                        if (!state.isLoading){
                            LazyRow {
                                items(state.listCategories) { category ->
                                    Box(
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp)
                                            .background(color =if (isChoiceCategory == category) primary else Color.White) // Add a background color for better border visibility
                                            .border(
                                                width = 1.5.dp, // Border width
                                                color = if (isChoiceCategory == category) primary else black, // Border color
                                                shape = RoundedCornerShape(4.dp) // Border corner radius
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = category.toUpperCase(Locale.ROOT),
                                            fontSize = 14.sp,
                                            color = if (isChoiceCategory == category) white else black,
                                            fontWeight = if (isChoiceCategory == category) FontWeight.Bold else FontWeight.Normal,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.width(140.dp).padding(vertical = 10.dp).clickable {
                                                isChoiceCategory = category
                                                if(category == "All"){
                                                    productsViewModel.getProduct()
                                                }else{
                                                    productsViewModel.getProductByCategories(category)

                                                }

                                            }
                                        )
                                    }
                                }
                            }
                        }
                        if (state.isLoadingProduct) {
                            Box(modifier = Modifier.padding(top = 50.dp)) {
                                ProductsLoading()

                            }
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                content = {
                                    items(state.listProducts.size, key = { i -> state.listProducts[i].id } ) { index ->
                                        val data = state.listProducts[index]
                                        Box(
                                            modifier = Modifier.padding(
                                                start = if (index % 2 == 1) 6.dp else 0.dp,
                                                end = if (index % 2 == 0) 6.dp else 0.dp,
                                                bottom = 16.dp
                                            )
                                        ) {
                                            ProductItem(
                                                data, state.user?.id.toString(),navHostController);
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxHeight().padding(top = 50.dp)// Avoid wrapContentHeight to prevent performance issues
                            )
                        }
                    }
                )
            }
            }

        }

    }

fun Double.convert(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    format.maximumFractionDigits = 0
    return format.format(this)
}
@Composable
fun ProductItem(product: Product,userId:String ,navHostController: NavHostController) {

    Card(
        modifier = Modifier
            .padding(8.dp).shadow(elevation = 10.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth().clickable {
                navHostController.navigate("product_detail_screen/{productId}/{userID}".replace(oldValue = "{productId}", newValue = product.id.toString().replace(oldValue = "{userID}", newValue =userId )))
            },
        colors = CardDefaults.cardColors(
            containerColor = light1,
        ),        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
        Column {
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
        Row (modifier = Modifier.padding(4.dp), horizontalArrangement = Arrangement.End){

            Box (modifier = Modifier.background(color = primary)
                , contentAlignment = Alignment.Center){ Text(text = product.category.toUpperCase(Locale.ROOT), fontSize = 10.sp, color = white,fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.padding(4.dp))
            }

        }
    }
    Image(

        painter = rememberImagePainter(product.image),
        contentDescription = product.title,
        modifier = Modifier
            .fillMaxWidth()

            .height(180.dp)

    )
}
                         Spacer(modifier = Modifier.height(10.dp))

            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
              Row (verticalAlignment = Alignment.Top) {
                  Icon(
                      imageVector = Icons.TwoTone.Star,
                      contentDescription = "Star",
                      tint = o
                  )

                  Text(modifier = Modifier.padding(top = 5.dp),text = "${product.rating.rate}", fontSize = 12.sp,fontWeight = FontWeight.Bold)

              }
              Text(text = (product.price).convert(), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = primaryDark)

          }
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = product.title, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))



        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    ProductItem(product = Product(id = 1, title = "Test Prod",22149000.0, description = "sdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsndssdnjsnds", category = "oke", image = "", rating = Rating(0.2,2) ), navHostController = NavHostController(
        LocalContext.current), userId = "1"
    );
}