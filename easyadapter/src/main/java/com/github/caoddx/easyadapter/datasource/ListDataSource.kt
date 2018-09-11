package com.github.caoddx.easyadapter.datasource

import com.github.caoddx.easyadapter.plain.ListGroup

interface ListDataSource<T> {
    var group: ListGroup<T>?

    fun getSize(): Int

    fun getItem(position: Int): T
}