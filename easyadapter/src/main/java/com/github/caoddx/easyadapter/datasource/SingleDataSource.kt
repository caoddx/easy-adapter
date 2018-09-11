package com.github.caoddx.easyadapter.datasource

import com.github.caoddx.easyadapter.plain.SingleGroup

interface SingleDataSource<T> {
    var group: SingleGroup<T>?

    fun haveData(): Boolean

    fun getItem(): T
}