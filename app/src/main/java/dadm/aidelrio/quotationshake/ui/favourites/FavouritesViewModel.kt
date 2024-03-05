package dadm.aidelrio.quotationshake.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dadm.aidelrio.quotationshake.domain.model.Quotation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject
constructor(): ViewModel() {
    private val _quotationList : MutableStateFlow<List<Quotation>> = MutableStateFlow(generateRandomQuotations(20))
    val isDeleteAllMenuVisible = _quotationList.map { list ->
        list.isNotEmpty()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = true
    )
    val quotationList : StateFlow<List<Quotation>>
        get() = _quotationList.asStateFlow()

    // Para pruebas
    private fun generateRandomQuotations(nquotations: Int): List<Quotation> {
        val quotations = mutableListOf<Quotation>()

        repeat(nquotations) {
            val num = (0..99).random()
            if (num < 50)
                quotations.add(Quotation(id = "$num", text = "Quotation text #$num", author = "Albert Einstein"))
            else
                quotations.add(Quotation(id = "$num", text = "Quotation text #$num", author = "Anonymous"))

        }

        return quotations
    }

    /*
    private fun getFavouriteQuotations() : List<Quotation> {
        // Pruebas
        return generateRandomQuotations(20)
    }*/

    fun deleteAllFavouriteQuotations() {
        _quotationList.update { listOf() }
    }

    fun deleteQuotationAtPosition(position: Int) {
        val copyList = _quotationList.value.toList()
        _quotationList.update { copyList.minus(copyList[position]) }
    }

    fun addFavouriteQuotation(quotation: Quotation) {
        val copyList = _quotationList.value.toList()
        _quotationList.update { copyList.plus(quotation) }
    }

}