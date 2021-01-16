package com.praktek.aplikasibola.view.Favorited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.praktek.aplikasibola.R
import com.praktek.aplikasibola.Adapter.ViewAdapter
import com.praktek.aplikasibola.util.TypeFavorites



class FavoritesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

    private fun setupEnv() {
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)

            view.Adapter = ViewAdapter(supportFragmentManager,
                    mapOf(
                            getString(R.string.title_matches).capitalize() to FragmentTabFavorited.newInstance(TypeFavorites.MATCHES)
                    )
            )
            tab_layout.setupWithViewPager(view)
        }
    }
}