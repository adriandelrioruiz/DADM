package dadm.aidelrio.quotationshake.domain.model

data class Quotation(val id : String, val text : String, val author : String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Quotation) return false

        return text == other.text && author == other.author
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + author.hashCode()
        return result
    }
}
