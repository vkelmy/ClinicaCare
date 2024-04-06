package com.example.clinicacare.di

import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.models.Client
import com.example.clinicacare.core.data.models.Professional
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.core.data.repository.ClinicRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ClinicModule {

    @Singleton
    @Provides
    fun provideRealm(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                Appointment::class, Client::class, Professional::class
            )
        )
            .compactOnLaunch()
            .build()
        return Realm.open(config)
    }

    @Singleton
    @Provides
    fun provideClinicRepository(realm: Realm): ClinicRepository {
        return ClinicRepositoryImpl(realm)
    }
}