package dadm.aidelrio.quotationshake.di

import android.content.Context
import androidx.room.Room
import dadm.aidelrio.quotationshake.data.favourites.FavouritesContract
import dadm.aidelrio.quotationshake.data.favourites.FavouritesDao
import dadm.aidelrio.quotationshake.data.favourites.FavouritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavouritesProviderModule {

    @Provides
    @Singleton
    fun provideFavouritesDatabase(@ApplicationContext context : Context): FavouritesDatabase {
        return Room.databaseBuilder(
            context = context,
            name = FavouritesContract.DATABASE_NAME,
            klass = FavouritesDatabase::class.java,
        ).build()
    }

    @Provides
    fun provideDao(favouritesDatabase: FavouritesDatabase): FavouritesDao {
        return favouritesDatabase.favouritesDao()
    }
}