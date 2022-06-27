package org.pt2.laundry.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.pt2.laundry.model.Profile
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.unsplash.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ProfileApiService {
    @GET("search/photos?query=user&client_id=pnr_0qFMCqYn3h66od1pg1bk_i_ZR8ZQKG_Xg6cxcZU")
    suspend fun getProfile(): List<Profile>
}

object ProfileApi {
    val service: ProfileApiService by lazy {
        retrofit.create(ProfileApiService::class.java)
    }

    fun getHewanUrl(): String {
        return "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzMzkzMjV8MHwxfHNlYXJjaHwzfHx1c2VyfGVufDB8fHx8MTY1NjAxNDEwOA&ixlib=rb-1.2.1&q=80&w=400"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }