package com.devkazonovic.projects.thenews.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.setMainPageToolbar
import com.devkazonovic.projects.thenews.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val root = binding.root
        root.let {
            toolbar = binding.topAppBar
            drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
        }
        navController = findNavController()
        toolbar.setMainPageToolbar(navController,drawerLayout)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = FollowingFragment()
    }
}