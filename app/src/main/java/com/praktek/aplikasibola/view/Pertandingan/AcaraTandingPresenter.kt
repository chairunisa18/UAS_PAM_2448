package com.praktek.aplikasibola.view.Pertandingan

import com.google.gson.Gson

import com.praktek.aplikasibola.model.AcaraResponse
import com.praktek.aplikasibola.Api.ApiRepository
import com.praktek.aplikasibola.Api.TheSportDBApi

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AcaraTandingPresenter(private val view: AcaraTandingView,
                             private val apiRepository: ApiRepository,
                             private val gson: Gson) {

    fun getLeagueAll() {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getLeagueAll()),
                    LeagueResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showLeagueList(data)
            }
        }
    }

    fun getEventsNext(id: String) {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getEventsNext(id)),
                    AcaraResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showEventList(data.events)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }

    fun getEventsLast(id: String) {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getEventsLast(id)),
                    AcaraResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showEventList(data.events)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }
}