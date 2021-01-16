package com.praktek.aplikasibola.view.Favorited

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.praktek.aplikasibola.R
import com.praktek.aplikasibola.Adapter.TandingAdapter
import com.praktek.aplikasibola.model.AcaraItem
import com.praktek.aplikasibola.util.invisible
import com.praktek.aplikasibola.util.visible
import com.praktek.aplikasibola.view.DetailTanding.DetailTandingActivity
import com.praktek.aplikasibola.util.TypeFavorites

import org.jetbrains.anko.bundleOf

class FragmentTabFavorited : Fragment(), FavoritesTabsView {

    companion object {
        private const val TYPE_FAVORITES = "TYPE_FAVORITES"

        fun newInstance(fragmentType: TypeFavorites): FragmentTabFavorited {
            val fragment = FragmentTabFavorited()
            fragment.arguments = bundleOf(TYPE_FAVORITES to fragmentType)

            return fragment
        }
    }

    private lateinit var fragmentType: TypeFavorites

    private lateinit var presenter: FavoritesTabsPresenter

    private lateinit var events: MutableList<AcaraItem>
    private lateinit var eventsAdapter: TandingAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pertandingan_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }



    override fun showLoading() {
        progress_bar.visible()
        recycler_view.invisible()
        tv_empty.invisible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
        recycler_view.visible()
        tv_empty.invisible()
    }

    override fun showEmptyData() {
        progress_bar.invisible()
        recycler_view.invisible()
        tv_empty.visible()
    }

    override fun showEventList(data: MutableList<AcaraItem>) {
        events.clear()
        events.addAll(data)
        eventsAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }


    private fun setupEnv() {
        fragmentType = arguments?.get(TYPE_FAVORITES) as TypeFavorites
        presenter = FavoritesTabsPresenter(context, this)

        when (fragmentType) {
            TypeFavorites.MATCHES -> {
                events = mutableListOf()
                eventsAdapter = TandingAdapter(events) {
                  DetailTandingActivity.start(context, it)
                }
            }

        }

        with(recycler_view) {
            adapter = if (fragmentType == TypeFavorites.MATCHES) eventsAdapter else teamsAdapter
            layoutManager = LinearLayoutManager(context)
            if (fragmentType == TypeFavorites.TEAMS) addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        if (fragmentType == TypeFavorites.MATCHES) presenter.getFavoritedEvents()
        else presenter.getFavoritedTeams()
    }
}
