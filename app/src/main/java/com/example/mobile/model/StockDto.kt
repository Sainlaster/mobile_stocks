package com.example.mobile.model

data class StockDto(
    var id:String,
    var ticket:String,
    var name:String,
    var about_company:String,
    var price:String,
    var lotSize:String,
    var priceStep:String,
    var isShort:String,
    var logo:String,
    var graphic_function:String
)
