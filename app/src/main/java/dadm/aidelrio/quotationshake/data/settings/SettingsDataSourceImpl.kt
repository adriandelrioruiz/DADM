package dadm.aidelrio.quotationshake.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SettingsDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsDataSource {

    private val lang = stringPreferencesKey("language_preference")
    private val user = stringPreferencesKey("username_preference")
    override fun getUserName(): Flow<String> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }.map { preferences ->
            preferences[user].orEmpty()
        }
    }

    override suspend fun getUserNameSnapshot(): String {
         return dataStore.data.first()[user].orEmpty()
    }

    override suspend fun setUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[user] = userName
        }
    }

    override fun getLanguage(): Flow<String> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }.map { preferences ->
            preferences[lang].orEmpty()
        }
    }

    override suspend fun getLanguageSnapshot(): String {
        return dataStore.data.first()[lang]  ?: "en"
    }

    override suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[lang] = language
        }
    }
}