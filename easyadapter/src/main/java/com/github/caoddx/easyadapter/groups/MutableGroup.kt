package com.github.caoddx.easyadapter.groups

import android.support.annotation.LayoutRes
import android.view.View
import java.util.*

class MutableGroup<T>(@LayoutRes layoutId: Int, items: List<T> = emptyList(), onBind: BaseGroup<T>.(itemView: View, position: Int) -> Unit) : BaseGroup<T>(layoutId, onBind) {

    private var items = LinkedList<T>()

    init {
        this.items.addAll(items)
    }

    override fun getItem(position: Int): T {
        return items[position]
    }

    override val size: Int
        get() = items.size

    fun clear() {
        val oldSize = size
        items.clear()
        notifyDataSetChanged(oldSize)
    }

    fun replace(items: List<T>) {
        val oldSize = size
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged(oldSize)
    }

    fun replaceAt(index: Int, newItem: T) {
        items[index] = newItem
        notifyItemChanged(index)
    }

    fun add(index: Int, item: T) {
        items.add(index, item)
        notifyItemInserted(index)
    }

    fun addFirst(item: T) {
        items.addFirst(item)
        notifyItemInserted(0)
    }

    fun addLast(item: T) {
        items.addLast(item)
        notifyItemInserted(size - 1)
    }

    fun removeAt(index: Int): T {
        val item = items.removeAt(index)
        notifyItemRemoved(index)
        return item
    }

    fun removeFirst(): T {
        val item = items.removeFirst()
        notifyItemRemoved(0)
        return item
    }

    fun removeLast(): T {
        val item = items.removeLast()
        notifyItemRemoved(size)
        return item
    }
}