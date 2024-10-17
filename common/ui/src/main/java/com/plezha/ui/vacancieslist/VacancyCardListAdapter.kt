package com.plezha.ui.vacancieslist

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.plezha.data.model.Vacancy

class VacancyCardListAdapter(
    onVacancyFavoriteStatusChanged: (Vacancy, Boolean) -> Unit,
    onVacancyOpenRequest: (Vacancy) -> Unit
) : ListDelegationAdapter<List<Vacancy>>() {
    init {
        delegatesManager.addDelegate(
            VacancyCardAdapterDelegate(
                onVacancyFavoriteStatusChanged,
                onVacancyOpenRequest
            )
        )
    }
}