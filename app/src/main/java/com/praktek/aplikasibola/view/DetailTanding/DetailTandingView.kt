package com.praktek.aplikasibola.view.DetailTanding

import com.praktek.aplikasibola.model.TimItem

interface DetailTandingView {

    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(dataHomeTeam: MutableList<TimItem>, dataAwayTeam: MutableList<TimItem>)
}
