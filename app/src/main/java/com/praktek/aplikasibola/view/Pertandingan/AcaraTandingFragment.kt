package com.praktek.aplikasibola.view.Pertandingan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.praktek.aplikasibola.R
import com.praktek.aplikasibola.Adapter.TandingAdapter
import com.praktek.aplikasibola.model.AcaraItem
import com.praktek.aplikasibola.model.LeagueResponse
import com.praktek.aplikasibola.model.LeaguesItem
import com.praktek.aplikasibola.Api.ApiRepository
import com.praktek.aplikasibola.util.*
import com.praktek.aplikasibola.view.DetailTanding.DetailTandingActivity
import org.jetbrains.anko.bundleOf

class AcaraTandingFragment : Fragment(), AcaraTandingView {

    companion object {
        private const val TYPE_MATCHES = "TYPE_MATCHES"

        fun newInstance(fragmentType: TypePertandingan): AcaraTandingFragment {
            val fragment = AcaraTandingFragment()
            fragment.arguments = bundleOf(TYPE_MATCHES to fragmentType)

            return fragment
        }
    }

    private lateinit var fragmentType: TypePertandingan

    private lateinit var presenter: AcaraTandingPresenter

    private lateinit var league: LeaguesItem

    private lateinit var events: MutableList<AcaraItem>
    private lateinit var listAdapter: TandingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_acara_pertandingan, container, false)
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

    override fun showLeagueList(data: LeagueResponse) {
        spinner.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as LeaguesItem

                when (fragmentType) {
                    TypePertandingan.NEXT -> presenter.getEventsNext(league.idLeague.toString())
                    TypePertandingan.LAST -> presenter.getEventsLast(league.idLeague.toString())
                }
            }
        }
    }

    override fun showEventList(data: MutableList<AcaraItem>) {
        events.clear()
        events.addAll(data)
        listAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupEnv() {
        fragmentType = arguments?.get(TYPE_MATCHES) as TypePertandingan
        presenter = AcaraTandingPresenter(this, ApiRepository(), Gson())

        spinner.loadFirstText(context!!)

        events = mutableListOf()
        listAdapter = MatchesAdapter(events) {
            DetailTandingActivity.start(context, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        presenter.getLeagueAll()
    }
}
