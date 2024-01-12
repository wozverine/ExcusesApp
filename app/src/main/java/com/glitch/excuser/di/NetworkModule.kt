package com.glitch.excuser.di

import android.content.Context
import com.glitch.excuser.common.Constants.BASE_URL
import com.glitch.excuser.data.source.remote.ExcuseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	@Singleton
	@Provides
	fun provideOkHttp(
		@ApplicationContext context: Context
	): OkHttpClient = OkHttpClient.Builder().apply {
		addInterceptor { chain ->
			val sharedPref = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
			val language = sharedPref.getString("language", "eng") ?: "eng"

			val builder = chain.request().newBuilder()
			builder.header("language", language)

			return@addInterceptor chain.proceed(builder.build())
		}
	}.build()

	@Singleton
	@Provides
	fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder().apply {
		addConverterFactory(GsonConverterFactory.create())
		baseUrl(BASE_URL)
		client(okHttpClient)
	}.build()

	@Singleton
	@Provides
	fun provideExcuseService(retrofit: Retrofit) = retrofit.create(ExcuseService::class.java)
}