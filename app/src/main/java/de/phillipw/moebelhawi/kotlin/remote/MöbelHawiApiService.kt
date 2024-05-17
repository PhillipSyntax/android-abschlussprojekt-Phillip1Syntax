package de.phillipw.moebelhawi.kotlin.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.phillipw.moebelhawi.kotlin.data.models.ProductResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://serpapi.com/"

private val logger = HttpLoggingInterceptor().apply {
    level =
        HttpLoggingInterceptor.Level.BODY
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .build()

interface MöbelHawiApiService {


    @GET("search.json?")
    suspend fun getProducts(
        @Query("engine") engine: String,
        @Query("hd_sort") hdSort: String,
        @Query("q") q: String,
        @Query("api_key") apiKey: String
    ):ProductResponse

}
object MöbelHawiApi {
    val retrofitService: MöbelHawiApiService by lazy { retrofit.create(MöbelHawiApiService::class.java) }
}