package jp.cordea.emvcontactlessreader

class Pdol(
) {
    companion object {
        private val START = listOf(0x9F, 0x38).map { it.toByte() }

        fun from(data: ByteArray) {
            val index = data.indices
                .indexOfFirst { data[it] == START[0] && data[it + 1] == START[1] }
            val length = data[index + 2].toInt()
            val startIndex = index + 3
            val rawPdol = data.slice(startIndex until startIndex + length)
        }
    }
}
