package jp.cordea.emvcontactlessreader

import android.nfc.Tag
import android.nfc.tech.IsoDep
import androidx.lifecycle.ViewModel

class FirstViewModel : ViewModel() {
    fun handleTag(tag: Tag) {
        IsoDep.get(tag).use {
            it.connect()
            val command = SelectCommand(it)
            val adf = command.select(ApplicationIdentifier.VISA)
            val status = Status.from(adf)
            if (status is Failure) {
                return
            }
            val tags = Pdol.extractTags(adf)
            val response = GpoCommand(it).gpo(tags.map { it.fill() }.flatten())
        }
    }
}
