package de.phillipw.moebelhawi.kotlin.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import de.phillipw.moebelhawi.kotlin.data.util.UrlListConverter
import de.phillipw.moebelhawi.kotlin.remote.BASE_URL

@Entity(tableName = "products")
@TypeConverters(UrlListConverter::class)
data class Product(
    @PrimaryKey
    @Json(name = "product_id")
    @ColumnInfo("product_id")
    val productId: String,
    val title: String,
    @Ignore
    val thumbnails: List<List<String>> = emptyList(),
    val price: Double,
    val thumbnailsSmall: List<String> = emptyList(),
    val thumbnailsBig: List<String> = emptyList()
) {
    constructor(
        productId: String,
        title: String,
        price: Double,
        thumbnailsSmall: List<String>,
        thumbnailsBig: List<String>
    ) : this(
        productId,
        title,
        emptyList(),
        price,
        thumbnailsSmall,
        thumbnailsBig
    )
}