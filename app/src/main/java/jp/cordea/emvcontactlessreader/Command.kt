package jp.cordea.emvcontactlessreader

import android.nfc.tech.IsoDep

sealed class Command

class SelectCommand(private val dep: IsoDep) : Command() {
    companion object {
        private val SELECT = arrayOf(0x00, 0xA4, 0x04, 0x00)
    }

    fun select(aid: ApplicationIdentifier): ByteArray {
        val length = arrayOf(aid.id.size)
        return dep.transceive(
            (SELECT + length + aid.id + arrayOf(0x00))
                .map { it.toByte() }
                .toByteArray()
        )
    }
}

class ReadRecordCommand(private val dep: IsoDep) : Command() {
    companion object {
        private val READ_RECORD = arrayOf(0x00, 0xB2)
    }

    fun read(number: Int, param: Int): ByteArray {
        return dep.transceive(
            (READ_RECORD + arrayOf(number, param) + arrayOf(0x00))
                .map { it.toByte() }
                .toByteArray()
        )
    }
}

class GpoCommand(private val dep: IsoDep) : Command() {
    companion object {
        private val GPO = arrayOf(0x80, 0xA8, 0x00, 0x00)
    }

    fun gpo(pdolData: List<Int>): ByteArray {
        val length = arrayOf(pdolData.size)
        val data = arrayOf(0x83) + length + pdolData
        val dataLength = arrayOf(data.size)
        return dep.transceive(
            (GPO + dataLength + data + arrayOf(0x00))
                .map { it.toByte() }
                .toByteArray()
        )
    }
}
