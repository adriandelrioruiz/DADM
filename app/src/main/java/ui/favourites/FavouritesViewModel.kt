package ui.favourites

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import domain.model.Quotation
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavouritesViewModel : ViewModel() {
    private val _quotationList : MutableStateFlow<List<Quotation>> = MutableStateFlow(getFavouriteQuotations())
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

}