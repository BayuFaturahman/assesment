package com.example.assesment.data.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)

data class Rating(
    val rate: Double,
    val count: Int
)

data class CartProduct(
    val id:String,
    val userId:String,
    val date:String,
    var products:List<ProductItem>,
    var productsDta:List<Product>?

)

data class CartProductBody(
    val userId:String,
    val date:String,
    val products:List<ProductItem>
)


data class ProductItem(
    val productId: Int,
    var quantity: Int
)

data class User(
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val name: Name,
    val address: Address,
    val phone: String
)

data class Name(
    val firstname: String,
    val lastname: String
)

data class Address(
    val city: String,
    val street: String,
    val number: Int,
    val zipcode: String,
    val geolocation: Geolocation
)

data class Geolocation(
    val lat: String,
    val long: String
)