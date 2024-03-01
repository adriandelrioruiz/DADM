package ui.newquotation

import android.util.Log
import androidx.constraintlayout.motion.utils.ViewState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Quotation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewQuotationViewModel : ViewModel() {

    data class ViewState (
        val userName : String,
        val quotation : Quotation?,
        var isLoading : Boolean,
        var favButtonVisible : Boolean
    )

    private val _viewState = MutableStateFlow(ViewState(getUserName(), null, false, false))

    private val _userName = MutableStateFlow(getUserName())
    private val _quotation = MutableStateFlow<Quotation?>(null)
    private val _isLoading = MutableStateFlow(false)
    private val _favButtonVisible = MutableStateFlow(false)

    private fun getUserName(): String {
        return setOf("Alice", "Bob", "Charlie", "David", "Emma", "").random()
    }

    fun addNewFavourite() {
        _favButtonVisible.value = false
    }

    fun getNewQuotation() {
        _viewState.value.isLoading = true
        val num = (0..99).random()

        _quotation.update {
            Quotation(
                id = "$num",
                text = "Quotation text #$num",
                author = "Author #$num"
            )
        }
        _viewState.value.favButtonVisible = true
        _viewState.value.isLoading = false


    }


    val userName: StateFlow<String>
        get() = _userName.asStateFlow()
    val quotation: StateFlow<Quotation?>
        get() = _quotation.asStateFlow()
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()
    val favButtonVisible: StateFlow<Boolean>
        get() = _favButtonVisible.asStateFlow()
    val viewState: StateFlow<ViewState>
        get() = _viewState.asStateFlow()







}