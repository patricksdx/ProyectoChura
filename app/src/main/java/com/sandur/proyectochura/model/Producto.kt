package com.sandur.proyectochura.model

data class Producto(
    val idproducto: Int,
    val idcategoria: Int,
    val nombreproducto: String,
    val cantidad: Int,
    val detalles: String,
    val precio: String,
    val preciodescuento: String?,
    val imagenproducto: String,
)

