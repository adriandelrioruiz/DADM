package dadm.aidelrio.quotationshake.data.favourites

import dadm.aidelrio.quotationshake.data.favourites.model.DatabaseQuotationDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouritesDataSourceImpl @Inject constructor(
    private val favouritesDao: FavouritesDao
): FavouritesDataSource {
    override suspend fun addQuote(quote: DatabaseQuotationDto) {
        favouritesDao.addQuote(quote)
    }

    override suspend fun deleteQuote(quote: DatabaseQuotationDto) {
        favouritesDao.deleteQuote(quote)

    }

    override fun getAllFavouriteQuotations(): Flow<List<DatabaseQuotationDto>> {
        return favouritesDao.getAllFavouriteQuotations()
    }

    override fun getQuote(id: String): Flow<DatabaseQuotationDto?> {
        return favouritesDao.getQuote(id)
    }

    override suspend fun deleteAllQuotes() {
        favouritesDao.deleteAllQuotes()
    }

}