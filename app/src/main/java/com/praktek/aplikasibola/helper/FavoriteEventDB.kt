package com.praktek.aplikasibola.helper

import android.content.Context
import android.database.sqlite.SQLiteConstraintException

import com.praktek.aplikasibola.model.AcaraItem

import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class FavoriteEventDB {

    fun addFavorites(context: Context, data: AcaraItem) {
        try {
            context.database.use {
                insert(AcaraItem.TABLE_EVENTS,
                        AcaraItem.ID_EVENT to data.idEvent,
                        AcaraItem.DATE to data.dateEvent,
                        AcaraItem.TIME to data.strTime,

                        // home team
                        AcaraItem.HOME_ID to data.idHomeTeam,
                        AcaraItem.HOME_TEAM to data.strHomeTeam,
                        AcaraItem.HOME_SCORE to data.intHomeScore,
                        AcaraItem.HOME_FORMATION to data.strHomeFormation,
                       AcaraItem.HOME_GOAL_DETAILS to data.strHomeGoalDetails,
                        AcaraItem.HOME_SHOTS to data.intHomeShots,
                        AcaraItem.HOME_LINEUP_GOALKEEPER to data.strHomeLineupGoalkeeper,
                        AcaraItem.HOME_LINEUP_DEFENSE to data.strHomeLineupDefense,
                        AcaraItem.HOME_LINEUP_MIDFIELD to data.strHomeLineupMidfield,
                        AcaraItem.HOME_LINEUP_FORWARD to data.strHomeLineupForward,
                        AcaraItem.HOME_LINEUP_SUBSTITUTES to data.strHomeLineupSubstitutes,

                        // away team
                        AcaraItem.AWAY_ID to data.idAwayTeam,
                        AcaraItem.AWAY_TEAM to data.strAwayTeam,
                        AcaraItem.AWAY_SCORE to data.intAwayScore,
                        AcaraItem.AWAY_FORMATION to data.strAwayFormation,
                        AcaraItem.AWAY_GOAL_DETAILS to data.strAwayGoalDetails,
                        AcaraItem.AWAY_SHOTS to data.intAwayShots,
                        AcaraItem.AWAY_LINEUP_GOALKEEPER to data.strAwayLineupGoalkeeper,
                        AcaraItem.AWAY_LINEUP_DEFENSE to data.strAwayLineupDefense,
                        AcaraItem.AWAY_LINEUP_MIDFIELD to data.strAwayLineupMidfield,
                        AcaraItem.AWAY_LINEUP_FORWARD to data.strAwayLineupForward,
                        AcaraItem.AWAY_LINEUP_SUBSTITUTES to data.strAwayLineupSubstitutes)
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun removeFavorites(context: Context, data:AcaraItem) {
        try {
            context.database.use {
                delete(AcaraItem.TABLE_EVENTS,
                        AcaraItem.ID_EVENT + " = {id}",
                        "id" to data.idEvent.toString())
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun isFavorite(context: Context, data:AcaraItem): Boolean {
        var bFavorite = false

        context.database.use {
            val favorites = select(AcaraItem.TABLE_EVENTS)
                    .whereArgs(AcaraItem.ID_EVENT + " = {id}",
                            "id" to data.idEvent.toString())
                    .parseList(classParser<AcaraItem>())

            bFavorite = !favorites.isEmpty()
        }

        return bFavorite
    }
}