package com.devkazonovic.projects.thenews.presentation.headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.devkazonovic.projects.thenews.databinding.FragmentHeadlineBinding


class HeadlineFragment : Fragment() {


    private var _binding: FragmentHeadlineBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlineBinding.inflate(inflater, container, false)
        val root = binding.root
        initViews()

        return root
    }

    private fun initViews() {
        binding.let {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = HeadlineFragment()
    }
}