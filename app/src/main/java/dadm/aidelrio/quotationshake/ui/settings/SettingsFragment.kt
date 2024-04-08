package dadm.aidelrio.quotationshake.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceDataStore
import androidx.preference.PreferenceFragmentCompat
import dadm.aidelrio.quotationshake.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var preferenceDataStore : PreferenceDataStore
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = preferenceDataStore
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)
    }


}