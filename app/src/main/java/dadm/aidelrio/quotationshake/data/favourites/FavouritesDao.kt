package dadm.aidelrio.quotationshake.data.favourites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dadm.aidelrio.quotationshake.data.favourites.FavouritesContract.Table
import dadm.aidelrio.quotationshake.data.favourites.model.DatabaseQuotationDto
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuote(quote : DatabaseQuotationDto)

    @Delete
    suspend fun deleteQuote(quote : DatabaseQuotationDto)

    @Query("SELECT * FROM ${Table.TABLE_NAME}")
    fun getAllFavouriteQuotations(): Flow<List<DatabaseQuotationDto>>

    @Query("SELECT * FROM ${Table.TABLE_NAME} WHERE ${Table.ID_COLUMN} = :id")
    fun getQuote(id : String) : Flow<DatabaseQuotationDto?>

    @Query("DELETE FROM ${Table.TABLE_NAME}")
    fun deleteAllQuotes()

}