package ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import domain.model.Quotation
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavouritesViewModel : ViewModel() {
    private val _quotationList : MutableStateFlow<List<Quotation>> = MutableStateFlow(getFavouriteQuotations())
    val isDeleteAllMenuVisible = _quotationList.map { list ->
        list.isNotEmpty()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = true
    )
    val quotationList : StateFlow<List<Quotation>>
        get() = _quotationList.asStateFlow()

    private fun getFavouriteQuotations() : List<Quotation> {
        val quotations = mutableListOf<Quotation>()

        repeat(20) {
            val num = (0..99).random()
            quotations.add(Quotation(id = "$num", text = "Quotation text #$num", author = "Author #$num"))
        }

        return quotations
    }

    fun deleteAllFavouriteQuotations() {
        _quotationList.value = listOf()
    }

}