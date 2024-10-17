package com.plezha.feature_search.ui.offerslist

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.plezha.data.model.Offer
import com.plezha.feature_search.R
import com.plezha.feature_search.databinding.OfferCardBinding


class OfferAdapterDelegate : AdapterDelegate<List<Offer>>() {

    override fun isForViewType(items: List<Offer>, position: Int): Boolean {
        return items[position] is Offer
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OfferCardBinding.inflate(inflater, parent, false) // Use View Binding
        return OfferCardViewHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<Offer>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position]
        (holder as OfferCardViewHolder).bind(item)
    }

    inner class OfferCardViewHolder(private val binding: OfferCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(offer: Offer) {
            binding.cardIcon.setImageResource(
                when(offer.id) {
                    "near_vacancies" -> {
                        binding.cardIcon.visibility = View.VISIBLE
                        R.drawable.ic_near_vacancies
                    }
                    "temporary_job" -> {
                        binding.cardIcon.visibility = View.VISIBLE
                        R.drawable.ic_temporary_job
                    }
                    "level_up_resume" -> {
                        binding.cardIcon.visibility = View.VISIBLE
                        R.drawable.ic_level_up_resume
                    }
                    else -> {
                        binding.cardIcon.visibility = View.INVISIBLE
                        R.drawable.ic_temporary_job // Any id is good
                    }
                }
            )
            binding.cardTitle.text = offer.title
            binding.cardTitle.maxLines = if (offer.button != null) 2 else 3

            if (offer.button?.text != null) {
                binding.cardButton.visibility = View.VISIBLE

                binding.cardButton.text = offer.button!!.text
                binding.cardButton.paintFlags =
                    binding.cardButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            } else {
                binding.cardButton.visibility = View.GONE
            }

            if (offer.link != null) {
                binding.root.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(offer.link))
                    startActivity(
                        binding.root.context,
                        Intent.createChooser(browserIntent, null),
                        null
                    );
                }
            }
        }
    }
}