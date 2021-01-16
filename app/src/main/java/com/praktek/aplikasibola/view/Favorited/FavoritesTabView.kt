package com.praktek.aplikasibola.view.Favorited

import com.praktek.aplikasibola.model.AcaraItem


interface FavoritesTabsView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showEventList(data: MutableList<AcaraItem>)
}
