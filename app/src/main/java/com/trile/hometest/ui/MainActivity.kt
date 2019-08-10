package com.trile.hometest.ui

import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.trile.hometest.R
import com.trile.hometest.recyclerview.ChipAdapter
import com.trile.hometest.ui.viewModel.ChipViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.chip_item_layout.*

class MainActivity : AppCompatActivity() {

    private lateinit var chipAdapter: ChipAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val mChipViewModel: ChipViewModel by lazy {
        ViewModelProviders.of(this).get(ChipViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        chip_list.layoutManager = linearLayoutManager

        mChipViewModel.mChipListLiveData.observe(this, Observer { chipList ->
            chipList?.let {
                chipAdapter.submitList(it)
            }
        })

        mChipViewModel.mErrorLiveData.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        chip_item.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                chip_item.viewTreeObserver.removeOnGlobalLayoutListener(this)
                chip_item.text = "Item\nName"
                chip_item.post {
                    chipAdapter = ChipAdapter(this@MainActivity, chip_item.measuredHeight)
                    chip_list.adapter = chipAdapter

                    mChipViewModel.loadData()
                }
            }
        })
    }
}
