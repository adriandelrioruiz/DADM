package dadm.aidelrio.quotationshake.data.favourites.model

import dadm.aidelrio.quotationshake.domain.model.Quotation

fun DatabaseQuotationDto.toDomain() =
    Quotation(
        id = id,
        text = text,
        author = author
    )

fun Quotation.toDatabaseDto() =
    DatabaseQuotationDto(
        id = id,
        text = text,
        author = author
    )