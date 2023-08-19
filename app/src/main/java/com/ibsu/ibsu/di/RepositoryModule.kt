package com.ibsu.ibsu.di
import com.ibsu.ibsu.data.repository.IBSURepositoryImpl
import com.ibsu.ibsu.domain.repository.IBSURepository
import javax.inject.Singleton
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMyRepository(
        myRepositoryImpl: IBSURepositoryImpl
    ): IBSURepository
}
