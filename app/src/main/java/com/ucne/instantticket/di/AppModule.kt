package com.ucne.instantticket.di

import android.content.Context
import androidx.room.Room
import com.ucne.instantticket.data.dataBase.UsuarioDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesUsuarioDatabase(@ApplicationContext appContext: Context): UsuarioDataBase =
        Room.databaseBuilder(appContext, UsuarioDataBase::class.java,"Usuario.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUsuarioDao(db:UsuarioDataBase) = db.UsuarioDao()

    @Provides
    fun provideEventoDao(db:UsuarioDataBase) = db.EventoDao()
}