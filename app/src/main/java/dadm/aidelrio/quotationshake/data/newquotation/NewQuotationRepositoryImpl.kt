package dadm.aidelrio.quotationshake.data.newquotation

import dadm.aidelrio.quotationshake.data.newquotation.model.toDomain
import dadm.aidelrio.quotationshake.data.settings.SettingsRepository
import dadm.aidelrio.quotationshake.domain.model.Quotation
import dadm.aidelrio.quotationshake.utils.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewQuotationRepositoryImpl @Inject constructor(
    private val newQuotationDataSource: NewQuotationDataSource,
    private val connectivityChecker: ConnectivityChecker,
    private val settingsRepository: SettingsRepository,

) : NewQuotationRepository {

    private lateinit var language: String
    init {
        CoroutineScope(SupervisorJob()).launch {
            settingsRepository.getLanguage().collect { languageCode ->
                language = languageCode.ifEmpty{ "en" }
            }
        }
    }

    override suspend fun getQuotation(): Result<Quotation> {
        return if (connectivityChecker.isConnectionAvailable())
            // FAKE
            // newQuotationDataSource.getQuotation(arrayOf("en", "ru", "xx").random()).toDomain()
            newQuotationDataSource.getQuotation(language).toDomain()
        else
            Result.failure(NoInternetException())
    }
}