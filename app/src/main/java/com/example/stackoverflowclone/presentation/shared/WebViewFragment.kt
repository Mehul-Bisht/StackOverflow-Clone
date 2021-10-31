package com.example.stackoverflowclone.presentation.shared

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.stackoverflowclone.databinding.FragmentWebViewBinding
import com.example.stackoverflowclone.utils.Browser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private val args: WebViewFragmentArgs by navArgs()

    companion object {

        @JvmStatic
        fun newInstance(param: String) =
            WebViewFragment().apply {
                arguments = bundleOf()
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = args.url
        val browser = Browser()

        binding.webview.apply {
            webViewClient = browser
            loadUrl(url)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}