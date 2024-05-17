package de.phillipw.moebelhawi.kotlin.data.models

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity(tableName = "product_table")

data class Product(
    @Json(name = "product_id")
    val productId: String,
    val title: String,
    val thumbnails: List<List<String>>,
    val price: Double
)