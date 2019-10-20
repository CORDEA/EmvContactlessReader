package jp.cordea.emvcontactlessreader

class AflData(
    val sfi: Int,
    val start: Int,
    val end: Int
) {
    companion object {
        private const val TAG = 0x94.toByte()

        fun parse(array: ByteArray): AflData {
            val index = array.indexOf(TAG)
            val start = index + 2
            val afl = array.slice(start until start + 3)
            return AflData(
                afl[0].toInt() or 0x04,
                afl[1].toInt(),
                afl[2].toInt()
            )
        }
    }
}
