package jp.cordea.emvcontactlessreader

sealed class Status {
    companion object {
        private val SUCCESS = listOf(0x90, 0x00).map { it.toByte() }

        fun from(data: ByteArray): Status {
            val status = data.takeLast(2)
            return if (status == SUCCESS) Success else Failure(status)
        }
    }
}

object Success : Status()

class Failure(val reason: List<Byte>) : Status()
