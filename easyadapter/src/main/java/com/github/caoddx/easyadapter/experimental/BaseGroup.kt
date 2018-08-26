package com.github.caoddx.easyadapter.experimental

import android.support.annotation.LayoutRes
import android.view.View
import com.github.caoddx.easyadapter.EasyAdapter
import com.github.caoddx.easyadapter.Group
import com.github.caoddx.easyadapter.groupsets.BaseGroupSet

abstract class BaseGroup<T>(@LayoutRes val layoutId: Int) : Group {

    var groupSet: BaseGroupSet? = null

    val adapter: EasyAdapter? get() = groupSet?.adapter

    internal abstract fun postOnBind(itemView: View, position: Int)

    abstract val size: Int

    abstract fun getItem(position: Int): T

    protected fun getAdapterPosition(position: Int): Int {
        TODO()
        // return (adapter?.getGroupStartPosition(this) ?: 0) + position
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

