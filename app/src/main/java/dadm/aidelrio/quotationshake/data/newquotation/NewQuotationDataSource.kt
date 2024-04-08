package dadm.aidelrio.quotationshake.data.newquotation

import dadm.aidelrio.quotationshake.data.newquotation.model.RemoteQuotationDto
import retrofit2.Response

interface NewQuotationDataSource  {
    suspend fun getQuotation(lang : String): Response<RemoteQuotationDto>
}