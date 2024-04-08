package dadm.aidelrio.quotationshake.data.favourites

import dadm.aidelrio.quotationshake.domain.model.Quotation
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    suspend fun addQuote(quote : Quotation)

    suspend fun deleteQuote(quote : Quotation)

    fun getAllFavouriteQuotations(): Flow<List<Quotation>>

    fun getQuote(id : String) : Flow<Quotation?>

    suspend fun deleteAllQuotes()
}