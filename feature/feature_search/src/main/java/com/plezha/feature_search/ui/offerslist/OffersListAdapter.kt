package com.plezha.feature_search.ui.offerslist

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.plezha.data.model.Offer

class OffersListAdapter : ListDelegationAdapter<List<Offer>>() {
    init {
        delegatesManager.addDelegate(OfferAdapterDelegate())
    }
}