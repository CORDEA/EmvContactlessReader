package jp.cordea.emvcontactlessreader

import android.nfc.Tag
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _tag = MutableLiveData<Tag>()
    val tag: LiveData<Tag> = _tag

    fun handleTag(tag: Tag) {
        _tag.value = tag
    }
}
