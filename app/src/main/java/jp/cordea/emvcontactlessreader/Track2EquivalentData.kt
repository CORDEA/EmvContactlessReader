package jp.cordea.emvcontactlessreader

class Track2EquivalentData(
    val pan: String,
    val expiredAt: String
) {
    companion object {
        val UNAVAILABLE = Track2EquivalentData("", "")

        private const val TAG = 0x57.toByte()

        fun parse(array: ByteArray): Track2EquivalentData {
            val first = array.indexOf(TAG)
            if (first < 0) {
                return UNAVAILABLE
            }
            val length = array[first + 1].toInt()
            val index = first + 2
            val data = array
                .slice(index until index + length)
                .joinToString("") { "%02x".format(it) }
                .split("d")
            val year = data[1].slice(0 until 2)
            val month = data[1].slice(2 until 4)
            return Track2EquivalentData(
                data.first(),
                "$month/$year"
            )
        }
    }
}
