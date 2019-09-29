package jp.cordea.emvcontactlessreader

import java.text.SimpleDateFormat
import java.util.*

sealed class PdolTag(rawTag: List<Int>) {
    private val tag = rawTag.map { it.toByte() }

    fun match(pair: Pair<Byte, Byte>): Boolean =
        if (tag.size > 1) {
            pair.toList() == tag
        } else {
            pair.first == tag.first()
        }

    abstract fun fill(): List<Int>

    class TerminalTransactionQualifiers : PdolTag(listOf(0x9F, 0x66)) {
        override fun fill(): List<Int> = throw NotImplementedError()
    }

    class AmountAuthorised() : PdolTag(listOf(0x9F, 0x02)) {
        override fun fill(): List<Int> = listOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
    }

    class AmountOther() : PdolTag(listOf(0x9F, 0x03)) {
        override fun fill(): List<Int> = listOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
    }

    class TerminalCountryCode() : PdolTag(listOf(0x9F, 0x1A)) {
        override fun fill(): List<Int> = listOf(0x03, 0x92)
    }

    class TerminalVerificationResults() : PdolTag(listOf(0x95)) {
        override fun fill(): List<Int> = listOf(0x00, 0x00, 0x00, 0x00, 0x00)
    }

    class TransactionCurrencyCode() : PdolTag(listOf(0x5F, 0x2A)) {
        override fun fill(): List<Int> = listOf(0x03, 0x92)
    }

    class TransactionDate() : PdolTag(listOf(0x9A)) {
        override fun fill(): List<Int> =
            SimpleDateFormat("yy MM dd", Locale.getDefault())
                .format(Date())
                .split(" ")
                .map { it.toInt(16) }
    }

    class TransactionType() : PdolTag(listOf(0x9C)) {
        override fun fill(): List<Int> = listOf(0x00)
    }

    class UnpredictableNumber() : PdolTag(listOf(0x9F, 0x37)) {
        override fun fill(): List<Int> = listOf(0x00, 0x00, 0x00, 0x10)
    }
}
