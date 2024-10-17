package com.plezha.ui.vacancieslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.plezha.data.model.Vacancy
import com.plezha.ui.R
import com.plezha.ui.databinding.VacancyCardBinding
import com.plezha.ui.monthNumberToPublishedStringResource
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class VacancyCardAdapterDelegate(
    private val onVacancyFavoriteStatusChanged: (Vacancy, Boolean) -> Unit,
    private val onVacancyOpenRequest: (Vacancy) -> Unit
) : AdapterDelegate<List<Vacancy>>() {

    override fun isForViewType(items: List<Vacancy>, position: Int): Boolean {
        return items[position] is Vacancy
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VacancyCardBinding.inflate(inflater, parent, false)
        return VacancyCardViewHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<Vacancy>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position]
        (holder as VacancyCardViewHolder).bind(item)
    }

    inner class VacancyCardViewHolder(
        private val binding: VacancyCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vacancy: Vacancy) {
            val context = binding.root.context

            binding.favoriteIcon.setImageResource(
                if (vacancy.isFavorite == true) {
                    R.drawable.ic_favorite
                } else {
                    R.drawable.ic_not_favorite
                }
            )

            if (vacancy.lookingNumber != null) {
                binding.vacancyPeopleWatchingText.visibility = View.VISIBLE
                binding.vacancyPeopleWatchingText.text = context.resources.getQuantityString(
                    R.plurals.people_watching, vacancy.lookingNumber!!, vacancy.lookingNumber!!
                )
            } else {
                binding.vacancyPeopleWatchingText.visibility = View.GONE
            }

            binding.vacancyTitle.text = vacancy.title
            binding.vacancyCity.text = vacancy.address?.town
            binding.vacancyCompany.text = vacancy.company
            binding.workExperience.text = vacancy.experience?.previewText
            setDatePublished(context, vacancy.publishedDate)

            binding.favoriteIcon.setOnClickListener {
                onVacancyFavoriteStatusChanged(vacancy, vacancy.isFavorite?.not() ?: true)
            }
            binding.root.setOnClickListener {
                onVacancyOpenRequest(vacancy)
            }
            binding.respondButton.setOnClickListener { }
        }

        private fun setDatePublished(context: Context, dateString: String?) {
            if (dateString == null) {
                binding.datePublished.visibility = View.GONE
                return
            }

            val (monthNumber, dayNumber) = getMonthAndDay(dateString)

            binding.datePublished.visibility = View.VISIBLE
            binding.datePublished.text = context
                .getString(monthNumberToPublishedStringResource(monthNumber), dayNumber)
        }

        private fun getMonthAndDay(dateString: String): Pair<Int, Int> {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = format.parse(dateString)

            val calendar = Calendar.getInstance()
            if (date != null) {
                calendar.time = date
            }

            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            return Pair(month, day)
        }
    }
}