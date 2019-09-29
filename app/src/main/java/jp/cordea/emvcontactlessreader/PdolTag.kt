package jp.cordea.emvcontactlessreader

import java.text.SimpleDateFormat
import java.util.*

sealed class PdolTag(rawTag: List<Int>) {
    companion object {
        fun from(data: List<Byte>): List<PdolTag> =
            data
                .zipWithNext()
                .mapNotNull {
                    when {
                        TerminalTransactionQualifiers.match(it) -> TerminalTransactionQualifiers
                        AmountAuthorised.match(it) -> AmountAuthorised
                        AmountOther.match(it) -> AmountOther
                        TerminalCountryCode.match(it) -> TerminalCountryCode
                        TerminalVerificationResults.match(it) -> TerminalVerificationResults
                        TransactionCurrencyCode.match(it) -> TransactionCurrencyCode
                        TransactionDate.match(it) -> TransactionDate
                        TransactionType.match(it) -> TransactionType
                        UnpredictableNumber.match(it) -> UnpredictableNumber
                        else -> null
                    }
                }
    }

    private val tag = rawTag.map { it.toByte() }

    fun match(pair: Pair<Byte, Byte>): Boolean =
        if (tag.size > 1) {
            pair.toList() == tag
        } else {
            pair.first == tag.first()
        }

    abstract fun fill(): List<Int>

    object TerminalTransactionQualifiers : PdolTag(listOf(0x9F, 0x66)) {
        override fun fill(): List<Int> = TtqBuilder().supportEmvMode().offlineOnly().build()
    }

    object AmountAuthorised : PdolTag(listOf(0x9F, 0x02)) {
        override fun fill(): List<Int> = listOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
    }

    object AmountOther : PdolTag(listOf(0x9F, 0x03)) {
        override fun fill(): List<Int> = listOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
    }

    object TerminalCountryCode : PdolTag(listOf(0x9F, 0x1A)) {
        override fun fill(): List<Int> = listOf(0x03, 0x92)
    }

    object TerminalVerificationResults : PdolTag(listOf(0x95)) {
        override fun fill(): List<Int> = listOf(0x00, 0x00, 0x00, 0x00, 0x00)
    }

    object TransactionCurrencyCode : PdolTag(listOf(0x5F, 0x2A)) {
        override fun fill(): List<Int> = listOf(0x03, 0x92)
    }

    object TransactionDate : PdolTag(listOf(0x9A)) {
        override fun fill(): List<Int> =
            SimpleDateFormat("yy MM dd", Locale.getDefault())
                .format(Date())
                .split(" ")
                .map { it.toInt(16) }
    }

    object TransactionType : PdolTag(listOf(0x9C)) {
        override fun fill(): List<Int> = listOf(0x00)
    }

    object UnpredictableNumber : PdolTag(listOf(0x9F, 0x37)) {
        override fun fill(): List<Int> = listOf(0x00, 0x00, 0x00, 0x10)
    }
}
