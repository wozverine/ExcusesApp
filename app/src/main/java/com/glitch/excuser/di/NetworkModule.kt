package com.glitch.excuser.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
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
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Language

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context) =
        ChuckerInterceptor.Builder(context).build()

	@Singleton
	@Provides
	@Language
	fun provideLanguage(): String {
		// TODO: Retrieve the selected language dynamically (from user preferences, system locale, etc.)
		return "eng" // Replace with actual logic to get the selected language
	}

	@Singleton
	@Provides
	fun provideOkHttp(chucker: ChuckerInterceptor, @Named("language") language: String): OkHttpClient =
		OkHttpClient.Builder().apply {
			addInterceptor {
				val builder = it.request().newBuilder()
				builder.header("language", language)
				return@addInterceptor it.proceed(builder.build())
			}
			addInterceptor(chucker)
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