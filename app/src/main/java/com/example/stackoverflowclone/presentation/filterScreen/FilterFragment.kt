package com.example.stackoverflowclone.presentation.filterScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.stackoverflowclone.databinding.FragmentFilterBinding
import com.example.stackoverflowclone.domain.models.Tags
import com.example.stackoverflowclone.presentation.shared.HomeViewModel
import com.example.stackoverflowclone.presentation.shared.HomeViewModelFactory
import com.example.stackoverflowclone.utils.Constant.FILTER
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilterFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    companion object {

        const val ACTION = "FilterFragmentItemSelect"
        const val KEY = "FilterFragmentItemSelectKey"

        @JvmStatic
        fun newInstance(param: String) =
            FilterFragment().apply {
                arguments = bundleOf()
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tags = arguments?.getParcelable<Tags>(FILTER)

        val adapter = FilterAdapter() {
            setFragmentResult(
                ACTION,
                bundleOf(KEY to it)
            )
            dismiss()
        }

        tags?.tags?.let {
            adapter.submitList(it)
        }
        binding.rvTags.adapter = adapter

        binding.btnClear.setOnClickListener {
            setFragmentResult(
                ACTION,
                bundleOf(KEY to null)
            )
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}