package com.praktek.aplikasibola.view.DetailTanding

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.praktek.aplikasibola.Api.ApiRepository
import com.praktek.aplikasibola.R
import com.praktek.aplikasibola.R.id.mn_favorites
import com.praktek.aplikasibola.helper.FavoriteEventDB
import com.praktek.aplikasibola.model.AcaraItem
import com.praktek.aplikasibola.util.*
import org.jetbrains.anko.*

abstract class DetailTandingActivity : AppCompatActivity(), DetailTandingView {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        private const val ID_HOME_BADGE = 0
        private const val ID_AWAY_BADGE = 1

        fun start(context: Context?, event: AcaraItem) {
            context?.startActivity<DetailTandingActivity>(EXTRA_PARAM to event)
        }
    }

    private lateinit var presenter: DetailPresenter

    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: ScrollView

    private lateinit var event: AcaraItem

    private var menuFavorites: Menu? = null
    private var isFavorite: Boolean = false

    private val favoritedEventsDb = FavoriteEventDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        event = intent.getParcelableExtra(EXTRA_PARAM)!!

        setupLayout(event)
        setupEnv(event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fav, menu)
        menuFavorites = menu
        setFavorite()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()

                true
            }

            mn_favorites -> {
                if (isFavorite) {
                    favoritedEventsDb.removeFavorites(ctx, event)
                } else {
                    favoritedEventsDb.addFavorites(ctx, event)
                }

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item!!)
        }
    }

    override fun showLoading() {
        progressBar.visible()
        scrollView.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        scrollView.visible()
    }



    private fun setupLayout(data:AcaraItem) {
        relativeLayout {
            scrollView = scrollView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)

                    // date
                    textCenter(DateTime.getDateFormat(data.dateEvent))
                    textCenter(DateTime.getTimeFormat(data.strTime))

                    // score
                    linearLayout {
                        gravity = Gravity.CENTER

                        textTitle(data.intHomeScore ?: "-")
                        textTitle(context.getString(R.string.title_vs))
                        textTitle(data.intAwayScore ?: "-")
                    }

                    // team
                    linearLayout {
                        layoutTeamBadge(ID_HOME_BADGE, data.strHomeTeam, data.strHomeFormation)
                                .lparams(matchParent, wrapContent, 1f)

                        layoutTeamBadge(ID_AWAY_BADGE, data.strAwayTeam, data.strAwayFormation)
                                .lparams(matchParent, wrapContent, 1f)
                    }

                    line()

                    layoutDetailItem("Goals", data.strHomeGoalDetails, data.strAwayGoalDetails)
                    layoutDetailItem("Shots", data.intHomeShots, data.intAwayShots)

                    line()

                    // lineups
                    textSubTitle("Lineups")

                    layoutDetailItem("Goal Keeper", data.strHomeLineupGoalkeeper, data.strAwayLineupGoalkeeper)
                    layoutDetailItem("Defense", data.strHomeLineupDefense, data.strAwayLineupDefense)
                    layoutDetailItem("Midfield", data.strHomeLineupMidfield, data.strAwayLineupMidfield)
                    layoutDetailItem("Forward", data.strHomeLineupForward, data.strAwayLineupForward)
                    layoutDetailItem("Substitutes", data.strHomeLineupSubstitutes, data.strAwayLineupSubstitutes)
                }
            }

            progressBar = progressBar().lparams {
                centerInParent()
            }
        }
    }

    private fun setupEnv(data: AcaraItem) {
        presenter = DetailPresenter(this, ApiRepository(), Gson())
        isFavorite = favoritedEventsDb.isFavorite(ctx, event)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"

        presenter.getTeamDetail(data.idHomeTeam.toString(), data.idAwayTeam.toString())
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bintangfull)
        } else {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bintangb)
        }
    }
}
