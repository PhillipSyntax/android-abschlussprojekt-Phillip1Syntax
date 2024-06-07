package de.phillipw.moebelhawi.kotlin.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_card_item")
data class ShoppingCartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("product_id")
    var productId: String,
    val quantity: Int = 1,
    @ColumnInfo
    val price: Double
)
