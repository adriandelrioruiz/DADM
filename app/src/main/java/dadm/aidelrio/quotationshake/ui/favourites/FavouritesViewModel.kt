package dadm.aidelrio.quotationshake.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dadm.aidelrio.quotationshake.data.favourites.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository
): ViewModel() {

    val quotationList = favouritesRepository.getAllFavouriteQuotations().stateIn(
        scope = viewModelScope,
        initialValue = listOf(),
        started = SharingStarted.WhileSubscribed()
    )

    // LISTA DE FAVORITAS FAKE
    /* private val _quotationList : MutableStateFlow<List<Quotation>> = MutableStateFlow(generateRandomQuotations(20))
    val isDeleteAllMenuVisible = _quotationList.map { list ->
        list.isNotEmpty()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = true
    )
    val quotationList : StateFlow<List<Quotation>>
        get() = _quotationList.asStateFlow()*/

    val isDeleteAllMenuVisible = quotationList.map { list ->
        list.isNotEmpty()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = true
    )


    fun deleteAllFavouriteQuotations() {
        viewModelScope.launch{
            withContext(Dispatchers.IO) {
                favouritesRepository.deleteAllQuotes()
            }
        }
    }

    fun deleteQuotationAtPosition(position: Int) {
        viewModelScope.launch{
            favouritesRepository.deleteQuote(quotationList.value[position])

        }
    }

    // MÉTODOS FAKE
    /* fun deleteAllFavouriteQuotations() {
        _quotationList.update { listOf() }
    }

    fun deleteQuotationAtPosition(position: Int) {
        val copyList = _quotationList.value.toList()
        _quotationList.update { copyList.minus(copyList[position]) }
    }

    fun addFavouriteQuotation(quotation: Quotation) {
        val copyList = _quotationList.value.toList()
        _quotationList.update { copyList.plus(quotation) }
    }*/


}