package com.example.clinicacare.di

import android.content.Context
import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.models.User
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.core.data.repository.ClinicRepositoryImpl
import com.example.clinicacare.core.data.repository.DatastoreRepository
import com.example.clinicacare.core.data.repository.DatastoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
                Appointment::class, User::class
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

    @Singleton
    @Provides
    fun provideDatastoreRepository(@ApplicationContext context: Context): DatastoreRepository {
        return DatastoreRepositoryImpl(context)
    }
}