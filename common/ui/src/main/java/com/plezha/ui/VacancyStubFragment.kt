package com.plezha.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.plezha.ui.databinding.FragmentVacancyStubBinding

class VacancyStubFragment : Fragment() {
    private var _binding: FragmentVacancyStubBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyStubBinding.inflate(
            inflater,
            container,
            false
        )
        val root: View = binding.root

        return root
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}