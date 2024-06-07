package de.phillipw.moebelhawi.kotlin.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import de.phillipw.moebelhawi.kotlin.data.util.UrlListConverter
import de.phillipw.moebelhawi.kotlin.remote.BASE_URL

@Entity(tableName = "products")
@TypeConverters(UrlListConverter::class)
data class Product(
    @Json(name = "product_id")
    @ColumnInfo("product_id")
    val productId: String,
    val title: String,
    @Ignore
    val thumbnails: List<List<String>>,
    val price: Double,
    val thumbnailsSmall: List<String>,
    val thumbnailsBig: List<String>
)