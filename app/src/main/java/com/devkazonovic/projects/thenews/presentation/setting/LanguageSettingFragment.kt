package com.devkazonovic.projects.thenews.presentation.setting

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.setUpNavigationGraph
import com.devkazonovic.projects.thenews.common.util.MaterialUtil
import com.devkazonovic.projects.thenews.databinding.FragmentLanguageSettingBinding
import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageSettingFragment : Fragment() {

    private var _binding: FragmentLanguageSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbar: Toolbar
    private lateinit var radioGroupList: RadioGroup
    private lateinit var tfSearch: TextInputLayout

    private val viewModel by
    hiltNavGraphViewModels<LanguageZoneViewModel>(R.id.languageSettingPage)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageSettingBinding.inflate(inflater, container, false)
        initViews()
        toolbar.setUpNavigationGraph(findNavController())
        toolbar.setNavigationIcon(R.drawable.ic_back)
        onSearchListener()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showList()
        viewModel.languageZoneList.observe(viewLifecycleOwner) {
            onSuccess(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        toolbar = binding.topAppBar
        radioGroupList = binding.radioGroupList
        tfSearch = binding.tfSearch
    }

    private fun onSuccess(list: List<LanguageZone>) {
        val layoutParams: RelativeLayout.LayoutParams =
            RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                this.setMargins(26, 26, 26, 26)
            }

        radioGroupList.removeAllViews()
        if (list.isNotEmpty()) {

            for (i in 0..list.lastIndex) {
                radioGroupList.addView(RadioButton(requireContext()).apply {
                    id = i
                    text = list[i].name
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setTextAppearance(
                            MaterialUtil.getStyleId(
                                requireContext(), R.attr.textAppearanceSubtitle2
                            )
                        )
                    }
                    if (list[i].getCeId() == viewModel.getCurrentLanguageZone()) {
                        isChecked = true
                    }
                }, layoutParams)
            }

            radioGroupList.setOnCheckedChangeListener { rdGroup, checkedId ->
                val checkedRadioButton = rdGroup.findViewById<RadioButton>(checkedId)
                if (checkedRadioButton != null) {
                    val isChecked = checkedRadioButton.isChecked
                    if (isChecked) {
                        viewModel.onLanguageZoneSelected(list[checkedId].getCeId())
                    }
                }

            }
        }
    }

    private fun onSearchListener() {
        tfSearch.editText?.doOnTextChanged { text, start, before, count ->
            viewModel.searchForLanguageZone(text?.trim().toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LanguageSettingFragment()
    }
}