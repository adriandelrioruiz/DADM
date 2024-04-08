package dadm.aidelrio.quotationshake.di

import dadm.aidelrio.quotationshake.data.settings.SettingsDataSource
import dadm.aidelrio.quotationshake.data.settings.SettingsDataSourceImpl
import dadm.aidelrio.quotationshake.data.settings.SettingsRepository
import dadm.aidelrio.quotationshake.data.settings.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsBinderModule {

    @Binds
    abstract fun bindSettingsDataSource(settingsDataSource: SettingsDataSourceImpl) : SettingsDataSource
    @Binds
    abstract fun bindSettingsRepository(settingsRepository: SettingsRepositoryImpl) : SettingsRepository
}