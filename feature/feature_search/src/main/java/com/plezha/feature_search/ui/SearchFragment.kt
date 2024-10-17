package com.plezha.feature_search.ui

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
import com.plezha.feature_search.R
import com.plezha.feature_search.databinding.FragmentSearchBinding
import com.plezha.feature_search.ui.offerslist.OffersListAdapter
import com.plezha.ui.HorizontalSpacingBetweenItemDecoration
import com.plezha.ui.dpToPx
import com.plezha.ui.vacancieslist.VacancyCardListAdapter
import com.plezha.ui.vacancieslist.VerticalSpacingBetweenItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val offersListAdapter = OffersListAdapter()
    private val vacancyCardListAdapter by lazy {
        VacancyCardListAdapter(
            onVacancyFavoriteStatusChanged =  { vacancy, newStatus ->
                searchViewModel.changeFavoriteStatus(vacancy, newStatus)
            },
            onVacancyOpenRequest = {
                findNavController().navigate(R.id.action_go_to_vacancy_fragment)
            }
        )
    }
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.offersRecycler.apply {
            adapter = offersListAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            addItemDecoration(HorizontalSpacingBetweenItemDecoration(
                dpToPx(this.context, 6))
            )
            itemAnimator = null
        }

        binding.vacanciesRecycler.apply {
            adapter = vacancyCardListAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            addItemDecoration(
                VerticalSpacingBetweenItemDecoration(
                dpToPx(this.context, 8))
            )
            itemAnimator = null
        }

        collectSearchScreenState()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

    private fun collectSearchScreenState() {
        lifecycleScope.launch {
            searchViewModel.searchScreenState.collect { searchScreenState ->
                when (searchScreenState) {
                    is SearchScreenState.Success -> {
                        loadSuccessfulState(searchScreenState)
                    }

                    is SearchScreenState.Error -> {
                        // TODO
                    }

                    is SearchScreenState.Loading -> {
                        binding.showMoreVacanciesButton.visibility = View.GONE
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun loadSuccessfulState(searchScreenState: SearchScreenState.Success) {
        binding.showMoreVacanciesButton.visibility = View.VISIBLE

        if (searchScreenState.offers.isNotEmpty()) {
            binding.offersRecycler.visibility = View.VISIBLE
            offersListAdapter.items = searchScreenState.offers
            offersListAdapter.notifyDataSetChanged()
        } else {
            binding.offersRecycler.visibility = View.GONE
        }

        if (searchScreenState.isFullListShown) {
            binding.offersSection.visibility = View.GONE
            binding.fullVacanciesListLowerRow.root.visibility = View.VISIBLE
            binding.showMoreVacanciesButton.visibility = View.GONE
            binding.searchBarIcon.setImageResource(R.drawable.ic_back)
            vacancyCardListAdapter.items = searchScreenState.vacancies
        } else {
            binding.offersSection.visibility = View.VISIBLE
            binding.fullVacanciesListLowerRow.root.visibility = View.GONE
            binding.showMoreVacanciesButton.visibility = View.VISIBLE
            binding.searchBarIcon.setImageResource(R.drawable.ic_search)
            vacancyCardListAdapter.items = searchScreenState.vacancies.subList(0, 3)
        }

        vacancyCardListAdapter.notifyDataSetChanged()

        binding.searchBarIcon.setOnClickListener {
            if (searchScreenState.isFullListShown) {
                searchViewModel.setIsFullListShown(false)
            }
        }

        binding.showMoreVacanciesButton.setOnClickListener {
            searchViewModel.setIsFullListShown(true)
        }

        val vacanciesAmount = searchScreenState.vacancies.size
        binding.fullVacanciesListLowerRow.vacanciesShownTextView.text =
            requireContext().resources.getQuantityString(
                com.plezha.ui.R.plurals.vacancies,
                vacanciesAmount,
                vacanciesAmount
            )
        binding.showMoreVacanciesTextView.text =
            "${getString(R.string.more)} ${
                requireContext().resources.getQuantityString(
                    com.plezha.ui.R.plurals.vacancies,
                    vacanciesAmount - 3,
                    vacanciesAmount - 3
                )
            }"
    }
}

