package com.plezha.feature_favorites.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plezha.feature_favorites.R
import com.plezha.feature_favorites.databinding.FragmentFavoritesBinding
import com.plezha.ui.dpToPx
import com.plezha.ui.vacancieslist.VacancyCardListAdapter
import com.plezha.ui.vacancieslist.VerticalSpacingBetweenItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val vacancyCardListAdapter by lazy {
        VacancyCardListAdapter(
            onVacancyFavoriteStatusChanged = { vacancy, newStatus ->
                favoritesViewModel.changeFavoriteStatus(vacancy, newStatus)
            },
            onVacancyOpenRequest = {
                findNavController().navigate(R.id.action_go_to_vacancy_fragment)
            }
        )
    }
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.favoritesRecycler.apply {
            adapter = vacancyCardListAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            addItemDecoration(
                VerticalSpacingBetweenItemDecoration(
                    dpToPx(this.context, 8)
                )
            )
            itemAnimator = null
        }

        collectFavoritesScreenState()

        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun collectFavoritesScreenState() {
        lifecycleScope.launch {
            favoritesViewModel.favoritesScreenState.collect { favoritesScreenState ->
                when (favoritesScreenState) {
                    is FavoritesScreenState.Success -> {
                        vacancyCardListAdapter.items = favoritesScreenState.vacancies
                        vacancyCardListAdapter.notifyDataSetChanged()

                        val favoriteVacanciesAmount = favoritesScreenState.vacancies.size
                        binding.favoritesSubtitle.text = requireContext().resources
                            .getQuantityString(
                                com.plezha.ui.R.plurals.vacancies,
                                favoriteVacanciesAmount,
                                favoriteVacanciesAmount
                            )
                    }

                    is FavoritesScreenState.Error -> {
                        // TODO
                    }

                    is FavoritesScreenState.Loading -> {
                        // TODO
                    }
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}