package com.praktek.aplikasibola.view.Favorited

import android.content.Context

import com.praktek.aplikasibola.helper.database
import com.praktek.aplikasibola.model.AcaraItem

import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoritesTabsPresenter(private val context: Context?,
                             private val view: FavoritesTabsView) {

    fun getFavoritedEvents() {
        view.showLoading()

        val data: MutableList<AcaraItem> = mutableListOf()

        context?.database?.use {
            val favorites = select(AcaraItem.TABLE_EVENTS)
                    .parseList(classParser<AcaraItem>())

            data.addAll(favorites)
        }

        view.hideLoading()

        if (data.size > 0) {
            view.showEventList(data)
        } else {
            view.showEmptyData()
        }
    }


    }

