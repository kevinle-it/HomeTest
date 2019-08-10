package com.trile.hometest.recyclerview

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.trile.hometest.R
import com.trile.hometest.databinding.ChipItemLayoutBinding
import com.trile.hometest.store.room.entity.ChipEntity
import kotlinx.android.synthetic.main.chip_item_layout.view.*

/**
 *
 * @author lmtri
 * @since Aug 05, 2019 at 9:22 AM
 */

class ChipAdapter(private val context: Context, private val maxItemHeight: Int) :
    RecyclerView.Adapter<ChipAdapter.ChipItemHolder>() {

    private val mChipItems = ArrayList<ChipEntity>()
    private var colorArray = ArrayList<Int>()

    init {
        colorArray = context.resources.getIntArray(R.array.color_array).toCollection(ArrayList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ChipItemHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.chip_item_layout,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ChipItemHolder, position: Int) {
        holder.binding.chip = mChipItems[position]
        holder.binding.root.chip_item.background.colorFilter =
            PorterDuffColorFilter(
                colorArray[position % colorArray.size],
                PorterDuff.Mode.SRC_ATOP
            )
        if (maxItemHeight > 0) {
            holder.binding.root.chip_item.height = maxItemHeight
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = mChipItems.size

    fun submitList(chipItems: List<ChipEntity>) {
        if (this.mChipItems.isEmpty()) {
            this.mChipItems.addAll(chipItems)
            notifyItemRangeInserted(0, chipItems.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@ChipAdapter.mChipItems.size
                }

                override fun getNewListSize(): Int {
                    return chipItems.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return chipItems[newItemPosition].id ==
                            this@ChipAdapter.mChipItems[oldItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return chipItems[newItemPosition].name ==
                            this@ChipAdapter.mChipItems[oldItemPosition].name
                }
            })
            this.mChipItems.clear()
            this.mChipItems.addAll(chipItems)
            result.dispatchUpdatesTo(this)
        }
    }

    class ChipItemHolder(val binding: ChipItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Toast.makeText(binding.root.context, binding.chip!!.name, Toast.LENGTH_SHORT)
        }
    }
}