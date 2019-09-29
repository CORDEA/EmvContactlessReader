package jp.cordea.emvcontactlessreader

class TtqBuilder {
    private val ttq = mutableListOf(0x00, 0x00, 0x00, 0x00)

    fun supportContactless() = apply {
        ttq[0] = ttq[0] or 0x40
    }

    fun supportEmvMode() = apply {
        ttq[0] = ttq[0] or 0x20
    }

    fun supportContact() = apply {
        ttq[0] = ttq[0] or 0x10
    }

    fun offlineOnly() = apply {
        ttq[0] = ttq[0] or 0x08
    }

    fun supportOnlinePin() = apply {
        ttq[0] = ttq[0] or 0x04
    }

    fun supportSignature() = apply {
        ttq[0] = ttq[0] or 0x02
    }

    fun supportOfflineDataAuthentication() = apply {
        ttq[0] = ttq[0] or 0x01
    }

    fun requestOnlineCryptogram() = apply {
        ttq[1] = ttq[1] or 0x80
    }

    fun requestCvm() = apply {
        ttq[1] = ttq[1] or 0x40
    }

    fun supportConsumerDevice() = apply {
        ttq[2] = ttq[2] or 0x40
    }

    fun supportFdda() = apply {
        ttq[3] = ttq[3] or 0x80
    }

    fun build(): List<Int> = ttq
}
