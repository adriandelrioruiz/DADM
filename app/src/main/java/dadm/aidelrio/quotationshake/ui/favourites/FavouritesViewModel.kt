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


}