package dadm.aidelrio.quotationshake.ui.newquotation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dadm.aidelrio.quotationshake.data.favourites.FavouritesRepository
import dadm.aidelrio.quotationshake.data.newquotation.NewQuotationRepository
import dadm.aidelrio.quotationshake.data.settings.SettingsRepository
import dadm.aidelrio.quotationshake.domain.model.Quotation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewQuotationViewModel @Inject constructor(
    private val newQuotationRepository: NewQuotationRepository,
    private val settingsRepository: SettingsRepository,
    private val favouritesRepository: FavouritesRepository
): ViewModel() {

    private val _error = MutableStateFlow<Throwable?>(null)
    private val _quotation = MutableStateFlow<Quotation?>(null)
    private val _isLoading = MutableStateFlow(false)


    fun resetError() {
        _error.update { null }
    }

    fun addNewFavourite() {
        viewModelScope.launch {
            _quotation.value?.let { favouritesRepository.addQuote(it) }
        }

    }

    fun getNewQuotation() {
        _isLoading.update { true }
        viewModelScope.launch {

            val randomNumber = Math.random()

            if (randomNumber < 0.5) {
                newQuotationRepository.getQuotation().fold(
                    onSuccess = { quotation -> _quotation.update { quotation }},
                    onFailure = { error -> _error.update { error } })
            } else {
                _quotation.update { Quotation("1", "Cita-repetida", "El-mismo") }
            }



        }

        _isLoading.update { false }
    }


    val userName: StateFlow<String>
        get() = settingsRepository.getUserName().stateIn(
            scope = viewModelScope,
            initialValue = "",
            started = SharingStarted.WhileSubscribed()
        )
    val quotation: StateFlow<Quotation?>
        get() = _quotation.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val favButtonVisible = quotation.flatMapLatest { currentQuotation ->
        if (currentQuotation == null) flowOf(false)
        else favouritesRepository.getQuote(currentQuotation.id)
            .map { quotationInDatabase ->
                quotationInDatabase == null
            }
    }.stateIn(
        scope = viewModelScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed()
    )
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()


    val error: StateFlow<Throwable?>
        get() = _error.asStateFlow()


}