package dadm.aidelrio.quotationshake.data.favourites

import dadm.aidelrio.quotationshake.data.favourites.model.toDatabaseDto
import dadm.aidelrio.quotationshake.data.favourites.model.toDomain
import dadm.aidelrio.quotationshake.domain.model.Quotation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val favouritesDataSource: FavouritesDataSource
): FavouritesRepository {
    override suspend fun addQuote(quote: Quotation) {
        favouritesDataSource.addQuote(quote.toDatabaseDto())
    }

    override suspend fun deleteQuote(quote: Quotation) {
        favouritesDataSource.deleteQuote(quote.toDatabaseDto())
    }

    override fun getAllFavouriteQuotations(): Flow<List<Quotation>> {

        val listResult = favouritesDataSource.getAllFavouriteQuotations().map { list ->
            list.map { databaseQuotationDto -> databaseQuotationDto.toDomain() }
        }

        return listResult

    }


    override fun getQuote(id: String): Flow<Quotation?> {
        return favouritesDataSource.getQuote(id).map { databaseQuotationDto -> databaseQuotationDto?.toDomain()
        }
    }

    override suspend fun deleteAllQuotes() {
        favouritesDataSource.deleteAllQuotes()
    }

}