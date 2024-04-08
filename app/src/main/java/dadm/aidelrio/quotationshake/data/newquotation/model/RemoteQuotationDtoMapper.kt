package dadm.aidelrio.quotationshake.data.newquotation.model

import dadm.aidelrio.quotationshake.domain.model.Quotation
import retrofit2.Response
import java.io.IOException

fun RemoteQuotationDto.toDomain() =
    Quotation(
        id = quoteLink,
        text = quoteText,
        author = quoteAuthor
    )

fun Quotation.toDomain() =
    RemoteQuotationDto(
        quoteLink = id,
        quoteText = text,
        quoteAuthor = author,
        senderLink = "",
        senderName = ""
    )

fun Response<RemoteQuotationDto>.toDomain() =
    if (isSuccessful) Result.success((body() as RemoteQuotationDto).toDomain())
    else Result.failure(IOException())