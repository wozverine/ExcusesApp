package com.glitch.excuser.di

import com.glitch.excuser.data.repository.ExcuseRepository
import com.glitch.excuser.data.source.remote.ExcuseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

	@Provides
	@Singleton
	fun provideExcuseRepository(
		excuseService: ExcuseService
	) = ExcuseRepository(excuseService)
}