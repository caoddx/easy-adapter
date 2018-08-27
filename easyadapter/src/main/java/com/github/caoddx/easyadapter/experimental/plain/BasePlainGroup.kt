package com.github.caoddx.easyadapter.experimental.plain

import android.support.annotation.LayoutRes
import com.github.caoddx.easyadapter.MixGroup
import com.github.caoddx.easyadapter.PlainGroup

abstract class BasePlainGroup<T>(@LayoutRes override val layoutId: Int) : PlainGroup<T> {

    override var mixGroup: MixGroup? = null

    protected fun getAdapterPosition(position: Int): Int {
        return (adapter?.getGroupStartPosition(this) ?: 0) + position
    }

    private fun performOnDataSetChanged() {
    }

    protected fun notifyAdapterDataSetChanged() {
        adapter?.notifyDataSetChanged()
        performOnDataSetChanged()
    }

    fun notifyDataSetChanged() {
        adapter?.notifyItemRangeChanged(getAdapterPosition(0), size)
        performOnDataSetChanged()
    }

    internal fun notifyDataSetChanged(oldSize: Int) {
        notifyItemRangeRemoved(0, oldSize)
        notifyItemRangeInserted(0, size)
    }

    protected fun notifyItemChanged(position: Int) {
        adapter?.notifyItemChanged(getAdapterPosition(position))
        performOnDataSetChanged()
    }

    protected fun notifyItemInserted(position: Int) {
        adapter?.notifyItemInserted(getAdapterPosition(position))
        performOnDataSetChanged()
    }

    protected fun notifyItemRemoved(position: Int) {
        adapter?.notifyItemRemoved(getAdapterPosition(position))
        performOnDataSetChanged()
    }

    protected fun notifyItemRangeInserted(position: Int, itemCount: Int) {
        adapter?.notifyItemRangeInserted(getAdapterPosition(position), itemCount)
        performOnDataSetChanged()
    }

    protected fun notifyItemRangeRemoved(position: Int, itemCount: Int) {
        adapter?.notifyItemRangeRemoved(getAdapterPosition(position), itemCount)
        performOnDataSetChanged()
    }
}

