package com.example.stackoverflowclone.presentation.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.stackoverflowclone.R
import com.example.stackoverflowclone.databinding.FragmentHomeBinding
import com.example.stackoverflowclone.extensions.gone
import com.example.stackoverflowclone.extensions.visible
import com.example.stackoverflowclone.presentation.filterScreen.FilterFragment
import com.example.stackoverflowclone.presentation.shared.HomeViewModel
import com.example.stackoverflowclone.presentation.shared.HomeViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    companion object {

        @JvmStatic
        fun newInstance(param: String) =
            HomeFragment().apply {
                arguments = bundleOf()
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(
            FilterFragment.ACTION,
        ) { _, bundle ->
            val tag = bundle.getString(FilterFragment.KEY)
            binding.query.text =
                tag ?: ""
            viewModel.setTag(tag)
        }

        val adapter = HomeFeedAdapter() {
            val action = HomeFragmentDirections.actionHomeFragmentToWebViewFragment(it)
            findNavController().navigate(action)
        }
        binding.rvQuestions.adapter = adapter

        lifecycleScope.launchWhenStarted {

            launch {
                viewModel.homeState.collect { state ->
                    when (state) {
                        is HomeViewModel.QuestionsResult.Loading -> {
                            binding.progressbar.visible()
                        }
                        is HomeViewModel.QuestionsResult.Success -> {
                            state.data?.let {
                                binding.progressbar.gone()
                                adapter.submitList(it)
                            }
                        }
                        is HomeViewModel.QuestionsResult.Error -> {
                            binding.progressbar.gone()
                            Snackbar.make(binding.root, state.message.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            launch {
                viewModel.avgState.collect { state ->
                    when (state) {
                        is HomeViewModel.AvgMetricsState.Initial -> {
                            binding.avgAnswerCount.gone()
                            binding.avgViewCount.gone()
                        }
                        is HomeViewModel.AvgMetricsState.Success -> {
                            binding.avgAnswerCount.text = String.format("Average Answer Count:\n%.2f",state.data.second)
                            binding.avgViewCount.text = String.format("Average View Count:\n%.2f",state.data.first)
                        }
                        is HomeViewModel.AvgMetricsState.Other -> {
                            binding.avgAnswerCount.gone()
                            binding.avgViewCount.gone()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}