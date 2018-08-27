package com.github.caoddx.easyadapter.experimental.datasource

import com.github.caoddx.easyadapter.experimental.plain.ListGroup

class ImmutableListDataSource<T>(val data: List<T>) : ListDataSource<T> {
    override var group: ListGroup<T>? = null

    override fun getSize(): Int {
        return data.size
    }

    override fun getItem(position: Int): T {
        return data[position]
    }
}