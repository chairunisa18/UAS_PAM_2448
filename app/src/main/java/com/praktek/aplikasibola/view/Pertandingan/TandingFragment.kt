package com.praktek.aplikasibola.view.Pertandingan


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.praktek.aplikasibola.R
import com.praktek.aplikasibola.Adapter.ViewAdapter
import com.praktek.aplikasibola.util.TypePertandingan


import org.jetbrains.anko.startActivity

class TandingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pertandingan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupEnv() {
        setHasOptionsMenu(true)

        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)

            viewadapter.adapter = ViewPagerAdapter(supportFragmentManager,
                    mapOf(
                            getString(R.string.title_next).capitalize() to AcaraTandingFragment.newInstance(TypePertandingan.NEXT),
                            getString(R.string.title_last).capitalize() to AcaraTandingFragment.newInstance(TypePertandingan.LAST)
                    )
            )
            tab_layout.setupWithViewPager(view_pager)
        }
    }
}
