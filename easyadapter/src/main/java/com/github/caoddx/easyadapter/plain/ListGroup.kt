package com.github.caoddx.easyadapter.plain

import android.support.annotation.LayoutRes
import android.view.View

class ListGroup<T>(@LayoutRes layoutId: Int, items: List<T>, onBind: BaseGroup<T>.(itemView: View, position: Int) -> Unit) : BaseGroup<T>(layoutId, onBind) {

    private var items: List<T> = items.toList()

    override fun getItem(position: Int): T {
        return items[position]
    }

    override val size: Int
        get() = items.size

    fun clear() {
        replace(emptyList())
    }

    fun replace(items: List<T>) {
        val oldSize = size
        this.items = items.toList()
        notifyDataSetChanged(oldSize)
    }
}