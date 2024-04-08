package dadm.aidelrio.quotationshake.data.favourites

import dadm.aidelrio.quotationshake.data.favourites.model.DatabaseQuotationDto
import kotlinx.coroutines.flow.Flow

interface FavouritesDataSource {

    suspend fun addQuote(quote : DatabaseQuotationDto)

    suspend fun deleteQuote(quote : DatabaseQuotationDto)

    fun getAllFavouriteQuotations(): Flow<List<DatabaseQuotationDto>>

    fun getQuote(id : String) : Flow<DatabaseQuotationDto?>

    suspend fun deleteAllQuotes()
}