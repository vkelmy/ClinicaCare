package com.example.clinicacare.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "clinic_key_store")

@Singleton
class DatastoreRepositoryImpl @Inject constructor(context: Context) : DatastoreRepository {

    private val datastore = context.datastore
    private val loginKey = booleanPreferencesKey("login_key")
    private val userIdKey = stringPreferencesKey("user_id")

    override suspend fun writeKeepLoginToDataStore(keepLogged: Boolean) {
        datastore.edit { store ->
            store[loginKey] = keepLogged
        }
    }

    override suspend fun readKeepLoginFromDataStore(): Flow<Boolean> {
        val preferences = datastore.data
        return flow {
            preferences.collect { pref ->
                emit(pref[loginKey] ?: false)
            }
        }
    }

    override suspend fun writeUserIdToDataStore(id: String) {
        datastore.edit { store ->
            store[userIdKey] = id
        }
    }

    override suspend fun readUserIdFromDataStore(): Flow<String> {
        val preferences = datastore.data
        return flow {
            preferences.collect { pref ->
                pref[userIdKey]?.let { emit(it) }
            }
        }
    }

    override suspend fun eraseDatastore() {
        datastore.edit {
            it.remove(loginKey)
            it.remove(userIdKey)
        }
    }
}