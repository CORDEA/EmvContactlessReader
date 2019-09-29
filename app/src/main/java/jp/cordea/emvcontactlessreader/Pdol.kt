package jp.cordea.emvcontactlessreader

object Pdol {
    private val START = listOf(0x9F, 0x38).map { it.toByte() }

    fun extractTags(data: ByteArray): List<PdolTag> {
        val index = data.indices
            .indexOfFirst { data[it] == START[0] && data[it + 1] == START[1] }
        val length = data[index + 2].toInt()
        val startIndex = index + 3
        val rawPdol = data.slice(startIndex until startIndex + length)
        return PdolTag.from(rawPdol)
    }
}
