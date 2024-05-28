package com.cha1se.githubsearcher.di

import com.cha1se.githubsearcher.data.GithubRepo
import com.cha1se.githubsearcher.data.GithubApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

val baseUrl = "https://api.github.com/"
val networkTime = 6000

fun provideGson(): Gson = GsonBuilder().setLenient().create()

fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }

    OkHttpClient
        .Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
} else {
    OkHttpClient
        .Builder()
        .build()
}

fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): GithubApi =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(GithubApi::class.java)

val dataModule = module {

    single { baseUrl }
    single { networkTime }
    single { provideGson() }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get(), get()) }
    single { GithubRepo(get()) }

}