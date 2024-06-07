package de.phillipw.moebelhawi.kotlin.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class ShoppingItemWithProductDetails (

    @Embedded
   val cartitem: ShoppingCartItem,

    @Relation(parentColumn = "product_id", entityColumn = "product_id")
    val product: Product
)
