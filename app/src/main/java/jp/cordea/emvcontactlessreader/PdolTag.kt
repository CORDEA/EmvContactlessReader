package jp.cordea.emvcontactlessreader

sealed class PdolTag(rawTag: List<Int>) {
    private val tag = rawTag.map { it.toByte() }

    fun match(pair: Pair<Byte, Byte>): Boolean =
        if (tag.size > 1) {
            pair.toList() == tag
        } else {
            pair.first == tag.first()
        }

    class TerminalTransactionQualifiers : PdolTag(listOf(0x9F, 0x66))

    class AmountAuthorised() : PdolTag(listOf(0x9F, 0x02)) {
    }

    class AmountOther() : PdolTag(listOf(0x9F, 0x03)) {
    }

    class TerminalCountryCode() : PdolTag(listOf(0x9F, 0x1A)) {
    }

    class TerminalVerificationResults() : PdolTag(listOf(0x95)) {
    }

    class TransactionCurrencyCode() : PdolTag(listOf(0x5F, 0x2A)) {
    }

    class TransactionDate() : PdolTag(listOf(0x9A)) {
    }

    class TransactionType() : PdolTag(listOf(0x9C)) {
    }

    class UnpredictableNumber() : PdolTag(listOf(0x9F, 0x37)) {
    }
}
