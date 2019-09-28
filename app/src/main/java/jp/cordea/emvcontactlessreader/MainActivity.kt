package jp.cordea.emvcontactlessreader

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val intent by lazy {
        PendingIntent.getActivity(
            this,
            0,
            Intent(this, this::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            0
        )
    }

    private val filters = arrayOf(IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED))
    private val techList = arrayOf(arrayOf(IsoDep::class.java.name))
    private val viewModel: MainViewModel by viewModels()

    private lateinit var adapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        adapter = NfcAdapter.getDefaultAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        adapter.enableForegroundDispatch(this, intent, filters, techList)
    }

    override fun onPause() {
        super.onPause()
        adapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.action != NfcAdapter.ACTION_TECH_DISCOVERED) {
            return
        }
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG) ?: return
        viewModel.handleTag(tag)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
