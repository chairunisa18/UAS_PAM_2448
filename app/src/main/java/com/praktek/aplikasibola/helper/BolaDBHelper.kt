package com.praktek.aplikasibola.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.praktek.aplikasibola.model.AcaraItem
import com.praktek.aplikasibola.model.AcaraResponse


import org.jetbrains.anko.db.*

class BolaDBHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Bola.db", null, 1) {

    companion object {
        private var instance: BolaDBHelper? = null

        @Synchronized
        fun getInstance(context: Context): BolaDBHelper{
            if (instance == null) {
                instance = BolaDBHelper(context.applicationContext)
            }

            return instance as BolaDBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(AcaraItem.TABLE_EVENTS, true,
                AcaraItem.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                AcaraItem.ID_EVENT to TEXT,
                AcaraItem.DATE to TEXT,
                AcaraItem.TIME to TEXT,

                // home team
                AcaraItem.HOME_ID to TEXT,
                AcaraItem.HOME_TEAM to TEXT,
                AcaraItem.HOME_SCORE to TEXT,
                AcaraItem.HOME_FORMATION to TEXT,
                AcaraItem.HOME_GOAL_DETAILS to TEXT,
                AcaraItem.HOME_SHOTS to TEXT,
                AcaraItem.HOME_LINEUP_GOALKEEPER to TEXT,
                AcaraItem.HOME_LINEUP_DEFENSE to TEXT,
                AcaraItem.HOME_LINEUP_MIDFIELD to TEXT,
                AcaraItem.HOME_LINEUP_FORWARD to TEXT,
                AcaraItem.HOME_LINEUP_SUBSTITUTES to TEXT,

                // away team
                AcaraItem.AWAY_ID to TEXT,
                AcaraItem.AWAY_TEAM to TEXT,
                AcaraItem.AWAY_SCORE to TEXT,
                AcaraItem.AWAY_FORMATION to TEXT,
                AcaraItem.AWAY_GOAL_DETAILS to TEXT,
                AcaraItem.AWAY_SHOTS to TEXT,
                AcaraItem.AWAY_LINEUP_GOALKEEPER to TEXT,
                AcaraItem.AWAY_LINEUP_DEFENSE to TEXT,
                AcaraItem.AWAY_LINEUP_MIDFIELD to TEXT,
                AcaraItem.AWAY_LINEUP_FORWARD to TEXT,
                AcaraItem.AWAY_LINEUP_SUBSTITUTES to TEXT)


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(AcaraItem.TABLE_EVENTS, true)

    }
}

val Context.database: BolaDBHelper
    get() = BolaDBHelper.getInstance(applicationContext)
