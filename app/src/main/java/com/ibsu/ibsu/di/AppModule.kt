package com.ibsu.ibsu.di

import android.content.Context
import android.util.Log.d
import com.ibsu.ibsu.data.remote.IBSUApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val url = request.url.toString()
            d("Request URL", url)
            chain.proceed(request)
        }
        .readTimeout(10, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/TengBSU/IBSU_JSONs/main/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMyApi(retrofit: Retrofit): IBSUApi {
        return retrofit.create(IBSUApi::class.java)
    }
}