package dadm.aidelrio.quotationshake.di

import dadm.aidelrio.quotationshake.data.newquotation.NewQuotationDataSource
import dadm.aidelrio.quotationshake.data.newquotation.NewQuotationDataSourceImpl
import dadm.aidelrio.quotationshake.data.newquotation.NewQuotationRepository
import dadm.aidelrio.quotationshake.data.newquotation.NewQuotationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NewQuotationBinderModule {
    @Binds
    abstract fun bindNewQuotationRepository(repositoryImpl: NewQuotationRepositoryImpl): NewQuotationRepository

    @Binds
    abstract fun bindNewQuotationDataSource(dataSourceImpl: NewQuotationDataSourceImpl): NewQuotationDataSource
}