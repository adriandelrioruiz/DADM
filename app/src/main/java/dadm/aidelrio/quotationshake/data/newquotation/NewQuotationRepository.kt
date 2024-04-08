package dadm.aidelrio.quotationshake.data.newquotation

import dadm.aidelrio.quotationshake.domain.model.Quotation

interface NewQuotationRepository {
    suspend fun getQuotation(): Result<Quotation>
}