package com.github.caoddx.easyadapter.groups

import android.support.annotation.LayoutRes
import android.view.View
import com.github.caoddx.easyadapter.groupsets.BaseGroupSet
import com.github.caoddx.easyadapter.Group
import com.github.caoddx.easyadapter.MultiAdapter

abstract class BaseGroup<T>(@LayoutRes val layoutId: Int, private val onBind: BaseGroup<T>.(itemView: View, position: Int) -> Unit) : Group {

    var groupSet: BaseGroupSet? = null

    val adapter: MultiAdapter? get() = groupSet?.adapter

    internal fun postOnBind(itemView: View, position: Int) {
        onBind(this, itemView, position)
    }

    abstract fun getItem(position: Int): T

    abstract val size: Int

    private val listeners: MutableList<(BaseGroup<*>) -> Unit> = mutableListOf()

    fun addOnDataSetChangedListener(onDataSetChanged: (BaseGroup<*>) -> Unit) {
        listeners.add(onDataSetChanged)
    }

    private fun performOnDataSetChanged() {
        listeners.forEach { it(this) }
    }

    protected fun getAdapterPosition(position: Int): Int {
        return (adapter?.getGroupStartPosition(this) ?: 0) + position
    }

    protected fun notifyAdapterDataSetChanged() {
        adapter?.notifyDataSetChanged()
        performOnDataSetChanged()
    }

    fun notifyDataSetChanged() {
        adapter?.notifyItemRangeChanged(getAdapterPosition(0), size)
        performOnDataSetChanged()
    }

    protected fun notifyDataSetChanged(oldSize: Int) {
        notifyItemRangeRemoved(0, oldSize)
        notifyItemRangeInserted(0, size)
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

