package org.tigeryan.notty.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREF_DATA_STORE_NAME = "pref_data_store"

val Context.dataStore by preferencesDataStore(
    name = PREF_DATA_STORE_NAME
)

fun <T> DataStore<Preferences>.getValue(key: Preferences.Key<T>): Flow<T?> =
    data.map { prefs ->
        prefs[key]
    }

fun <T> DataStore<Preferences>.getValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
    data.map { prefs ->
        prefs[key] ?: defaultValue
    }

suspend fun <T> DataStore<Preferences>.setValue(key: Preferences.Key<T>, value: T) {
    edit { prefs ->
        prefs[key] = value
    }
}
