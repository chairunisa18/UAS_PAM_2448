package com.praktek.aplikasibola.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.praktek.aplikasibola.R
import com.praktek.aplikasibola.view.Favorited.FavoritesFragment
import com.praktek.aplikasibola.view.Favorited.FragmentTabFavorited
import com.praktek.aplikasibola.view.Pertandingan.TandingFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEnv()
    }

    private fun setupEnv() {
        setFragment(TandingFragment())
        listenBottomNavigationView()
    }

    private fun listenBottomNavigationView() {
        val bottom_navigation_view
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                bottom_navigation_view.selectedItemId -> return@setOnNavigationItemSelectedListener false

                R.id.bnv_matches -> {
                    setFragment(TandingFragment())
                    return@setOnNavigationItemSelectedListener true
                }


                R.id.bnv_favorites -> {
                    setFragment(FragmentTabFavorited())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun setFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}

