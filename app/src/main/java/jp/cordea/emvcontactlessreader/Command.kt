package jp.cordea.emvcontactlessreader

import android.nfc.tech.IsoDep

sealed class Command

class SelectCommand(private val dep: IsoDep) : Command() {
    companion object {
        private val SELECT = arrayOf(0x00, 0xA4, 0x04, 0x00)
    }

    fun select(aid: Array<Int>): ByteArray {
        val length = arrayOf(aid.size)
        return dep.transceive(
            (SELECT + length + aid + arrayOf(0x00))
                .map { it.toByte() }
                .toByteArray()
        )
    }
}
