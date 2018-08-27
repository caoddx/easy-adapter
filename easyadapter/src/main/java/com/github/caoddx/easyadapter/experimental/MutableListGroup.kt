package com.github.caoddx.easyadapter.experimental

import android.support.annotation.LayoutRes
import android.view.View
import java.util.*

class MutableListGroup<T>(@LayoutRes layoutId: Int,
                          val dataSource: DataSource<T>,
                          val onBind: MutableListGroup<T>.(itemView: View, item: T, position: Int) -> Unit)
    : BaseGroup<T>(layoutId) {

    init {
        dataSource.group = this
    }

    override fun bindView(itemView: View, position: Int) {
        onBind(this, itemView, getItem(position), position)
    }

    override val size: Int
        get() = dataSource.getSize()

    override fun getItem(position: Int): T {
        return dataSource.getItem(position)
    }
}

interface DataSource<T> {
    var group: MutableListGroup<T>?

    fun getSize(): Int

    fun getItem(position: Int): T
}

class ListDataSource<T>(val data: List<T>) : DataSource<T> {
    override var group: MutableListGroup<T>? = null

    override fun getSize(): Int {
        return data.size
    }

    override fun getItem(position: Int): T {
        return data[position]
    }
}

class MutableDataSource<T> : DataSource<T> {

    override var group: MutableListGroup<T>? = null

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