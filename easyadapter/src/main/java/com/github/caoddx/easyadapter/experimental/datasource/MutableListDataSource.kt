package com.github.caoddx.easyadapter.experimental.datasource

import com.github.caoddx.easyadapter.experimental.plain.ListGroup
import java.util.*

class MutableListDataSource<T> : ListDataSource<T> {

    override var group: ListGroup<T>? = null

    private val items = LinkedList<T>()

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
}

