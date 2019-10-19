package jp.cordea.emvcontactlessreader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import jp.cordea.emvcontactlessreader.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: FirstViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFirstBinding.bind(view) ?: return
        binding.viewModel = viewModel

        activityViewModel.tag
            .observe(viewLifecycleOwner, Observer {
                viewModel.handleTag(it)
            })
    }
}
