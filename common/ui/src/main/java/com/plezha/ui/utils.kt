package com.plezha.ui

import android.content.Context
import android.util.DisplayMetrics

fun dpToPx(context: Context, dp: Int): Int {
    val displayMetrics: DisplayMetrics = context.resources?.displayMetrics ?: return dp
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

internal fun monthNumberToPublishedStringResource(monthNumber: Int) =
    when (monthNumber) {
        1 -> R.string.published_in_january
        2 -> R.string.published_in_february
        3 -> R.string.published_in_march
        4 -> R.string.published_in_april
        5 -> R.string.published_in_may
        6 -> R.string.published_in_june
        7 -> R.string.published_in_july
        8 -> R.string.published_in_august
        9 -> R.string.published_in_september
        10 -> R.string.published_in_october
        11 -> R.string.published_in_november
        12 -> R.string.published_in_december
        else -> throw IllegalArgumentException()
    }
