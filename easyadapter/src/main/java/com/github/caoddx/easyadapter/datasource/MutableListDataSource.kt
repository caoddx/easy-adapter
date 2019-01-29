package com.github.caoddx.easyadapter.datasource

import com.github.caoddx.easyadapter.plain.ListGroup
import java.util.*

class MutableListDataSource<T>(initData: List<T> = emptyList()) : ListDataSource<T> {

    override var group: ListGroup<T>? = null

    private val items = LinkedList<T>(initData)

    override fun getSize(): Int {
        return items.size
    }

    override fun getItem(position: Int): T {
        return items[position]
    }

    fun clear() {
        val oldSize = getSize()
        items.clear()
        group?.notifyDataSetChanged(oldSize)
    }

    fun replace(items: List<T>) {
        val oldSize = getSize()
        this.items.clear()
        this.items.addAll(items)
        group?.notifyDataSetChanged(oldSize)
    }

    fun refreshAt(index: Int, item: T) {
        items[index] = item
        group?.notifyItemChanged(index)
    }

    fun add(index: Int, item: T) {
        items.add(index, item)
        group?.notifyItemInserted(index)
    }

    fun addFirst(item: T) {
        items.addFirst(item)
        group?.notifyItemInserted(0)
    }

    fun addLast(item: T) {
        items.addLast(item)
        group?.notifyItemInserted(getSize() - 1)
    }

    fun removeAt(index: Int): T {
        val item = items.removeAt(index)
        group?.notifyItemRemoved(index)
        return item
    }

    fun removeFirst(): T {
        val item = items.removeFirst()
        group?.notifyItemRemoved(0)
        return item
    }

    fun removeLast(): T {
        val item = items.removeLast()
        group?.notifyItemRemoved(getSize())
        return item
    }
}

