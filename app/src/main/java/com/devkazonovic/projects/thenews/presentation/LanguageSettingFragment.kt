package com.devkazonovic.projects.thenews.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.setUpNavigationGraph
import com.devkazonovic.projects.thenews.databinding.FragmentLanguageSettingBinding


class LanguageSettingFragment : Fragment() {

    private var _binding: FragmentLanguageSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageSettingBinding.inflate(inflater, container, false)

        val root = binding.root
        root.let {
            toolbar = binding.topAppBar
        }

        toolbar.setUpNavigationGraph(findNavController())
        toolbar.setNavigationIcon(R.drawable.ic_back)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LanguageSettingFragment()
    }
}