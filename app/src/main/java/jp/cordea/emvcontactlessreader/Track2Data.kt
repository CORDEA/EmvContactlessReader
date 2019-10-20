package jp.cordea.emvcontactlessreader

class Track2Data(
    val pan: String,
    val expiredAt: String
) {
    companion object {
        private val TAG = arrayOf(0x9F, 0x6B).map { it.toByte() }

        fun parse(array: ByteArray): Track2Data {
            val first = array
                .toList()
                .zipWithNext()
                .indexOfFirst { (first, second) -> first == TAG[0] && second == TAG[1] }
            val length = array[first + 2].toInt()
            val index = first + 3
            val data = array.slice(index until index + length)
                .joinToString("") { "%02x".format(it) }
                .split("d")
            val year = data[1].slice(0 until 2)
            val month = data[1].slice(2 until 4)
            return Track2Data(
                data.first(),
                "$month/$year"
            )
        }
    }
}
