package jp.cordea.emvcontactlessreader

class Track2Data(
    val pan: String,
    val expiredAt: String
) {
    companion object {
        private const val TAG = 0x57.toByte()

        fun parse(array: ByteArray): Track2Data {
            val first = array.indexOf(TAG)
            val length = array[first + 1].toInt()
            val index = first + 2
            val data = array
                .slice(index until index + length)
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
