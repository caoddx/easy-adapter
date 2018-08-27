package com.github.caoddx.easyadapter.experimental.datasource

import com.github.caoddx.easyadapter.experimental.plain.ListGroup

interface ListDataSource<T> {
    var group: ListGroup<T>?

    fun getSize(): Int

    fun getItem(position: Int): T
}