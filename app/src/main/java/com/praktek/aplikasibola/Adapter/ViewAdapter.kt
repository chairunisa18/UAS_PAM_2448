package com.praktek.aplikasibola.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.praktek.aplikasibola.view.Favorited.FragmentTabFavorited

class ViewAdapter(supportFragmentManager: FragmentManager, private val map: Map<String, FragmentTabFavorited>) : FragmentStatePagerAdapter(supportFragmentManager) {

    private val titles = map.keys.toList()
    private val fragments = map.values.toList()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = map.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}