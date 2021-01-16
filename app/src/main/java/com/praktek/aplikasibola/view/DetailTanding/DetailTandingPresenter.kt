package com.praktek.aplikasibola.view.DetailTanding

import com.google.gson.Gson
import com.praktek.aplikasibola.model.TeamResponse
import com.praktek.aplikasibola.Api.ApiRepository
import com.praktek.aplikasibola.Api.TheSportDBApi

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailTandingView,
                      private val ApiRepository: ApiRepository,
                      private val gson: Gson) {

    fun getTeamDetail(idHomeTeam: String, idAwayTeam: String) {
        view.showLoading()

        doAsync {
            val dataHomeTeam = gson.fromJson(ApiRepository
                    .doRequest(TheSportDBApi.getTimDetail(idHomeTeam)),
                    TeamResponse::class.java
            )

            val dataAwayTeam = gson.fromJson(ApiRepository
                    .doRequest(TheSportDBApi.getTimDetail(idAwayTeam)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamDetail(dataHomeTeam.teams, dataAwayTeam.teams)
            }
        }
    }
}