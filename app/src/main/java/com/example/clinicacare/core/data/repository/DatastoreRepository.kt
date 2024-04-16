package com.example.clinicacare.core.data.repository

import kotlinx.coroutines.flow.Flow

interface DatastoreRepository {

    suspend fun writeKeepLoginToDataStore(keepLogged: Boolean)

    suspend fun readKeepLoginFromDataStore(): Flow<Boolean>

    suspend fun writeUserIdToDataStore(id: String)

    suspend fun readUserIdFromDataStore(): Flow<String>

    suspend fun eraseDatastore()
}