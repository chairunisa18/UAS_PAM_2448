package com.praktek.aplikasibola.view.Pertandingan

import com.praktek.aplikasibola.model.AcaraItem
import com.praktek.aplikasibola.model.LeagueResponse

interface AcaraTandingView{

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showLeagueList(data: LeagueResponse)
    fun showEventList(data: MutableList<AcaraItem>)
}