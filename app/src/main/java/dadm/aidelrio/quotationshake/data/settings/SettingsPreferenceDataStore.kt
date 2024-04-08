package dadm.aidelrio.quotationshake.data.settings

import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SettingsPreferenceDataStore @Inject constructor(
    private val settingsRepository: SettingsRepository
) : PreferenceDataStore() {

    override fun putString(key: String?, value: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            when (key) {
                "username_preference" -> value?.let { settingsRepository.setUserName(it) }
                "language_preference" -> value?.let { settingsRepository.setLanguage(it) }
            }
        }
    }

    override fun getString(key: String?, defValue: String?): String {
        var result: String?
        runBlocking(Dispatchers.IO) {
            result = when (key) {
                "username_preference" -> settingsRepository.getUserNameSnapshot()
                "language_preference" -> settingsRepository.getLanguageSnapshot()
                else -> null
            }
        }
        return result ?: defValue ?: ""
    }

}