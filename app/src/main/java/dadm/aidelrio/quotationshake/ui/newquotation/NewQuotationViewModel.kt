package dadm.aidelrio.quotationshake.ui.newquotation

import androidx.lifecycle.ViewModel
import dadm.aidelrio.quotationshake.domain.model.Quotation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewQuotationViewModel @Inject
constructor(): ViewModel() {

    private val _userName = MutableStateFlow(getUserName())
    private val _quotation = MutableStateFlow<Quotation?>(null)
    private val _isLoading = MutableStateFlow(false)
    private val _favButtonVisible = MutableStateFlow(false)

    private fun getUserName(): String {
        return setOf("Alice", "Bob", "Charlie", "David", "Emma", "").random()
    }

    fun addNewFavourite() {
        _favButtonVisible.update { false }
    }

    fun getNewQuotation() {
        _isLoading.update { true }
        val num = (0..99).random()

        _quotation.update {
            Quotation(
                id = "$num",
                text = "Quotation text #$num",
                author = "Author #$num"
            )
        }
        _favButtonVisible.update { true }
        _isLoading.update { false }
    }


    val userName: StateFlow<String>
        get() = _userName.asStateFlow()
    val quotation: StateFlow<Quotation?>
        get() = _quotation.asStateFlow()
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()
    val favButtonVisible: StateFlow<Boolean>
        get() = _favButtonVisible.asStateFlow()







}