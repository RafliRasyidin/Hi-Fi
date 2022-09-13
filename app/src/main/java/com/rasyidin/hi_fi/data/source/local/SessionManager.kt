package com.rasyidin.hi_fi.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.rasyidin.hi_fi.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = context.getString(R.string.hifi_preferences)
    )

    private val onBoardingKey = booleanPreferencesKey(context.getString(R.string.ON_BOARDING_KEY))

    suspend fun setOnBoardingState(isOnBoarded: Boolean) {
        context.dataStore.edit { pref ->
            pref[onBoardingKey] = isOnBoarded
        }
    }

    fun getOnBoardingState() = context.dataStore.data.map { pref ->
        pref[onBoardingKey] ?: false
    }
}