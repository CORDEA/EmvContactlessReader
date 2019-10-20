package jp.cordea.emvcontactlessreader

import android.nfc.Tag
import android.nfc.tech.IsoDep
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class FirstViewModel : ViewModel() {
    val pan = ObservableField<String>("")
    val expiredAt = ObservableField<String>("")

    fun handleTag(tag: Tag) {
        IsoDep.get(tag).use { dep ->
            dep.connect()
            val command = SelectCommand(dep)
            val adf = ApplicationIdentifier
                .values()
                .map { command.select(it) }
                .first { Status.from(it) is Success }
            val tags = Pdol.extractTags(adf)
            val response = GpoCommand(dep).gpo(tags.map { it.fill() }.flatten())
            val data = Track2Data.parse(response)

            pan.set(data.pan)
            expiredAt.set(data.expiredAt)
        }
    }
}
