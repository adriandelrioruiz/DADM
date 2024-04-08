package dadm.aidelrio.quotationshake.data.favourites.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dadm.aidelrio.quotationshake.data.favourites.FavouritesContract.Table

@Entity (tableName = Table.TABLE_NAME)
data class DatabaseQuotationDto(
    @PrimaryKey @ColumnInfo(name = Table.ID_COLUMN) val id : String,
    @ColumnInfo(name = Table.TEXT_COLUMN) val text: String,
    @ColumnInfo(name = Table.AUTHOR_COLUMN) val author: String
)
