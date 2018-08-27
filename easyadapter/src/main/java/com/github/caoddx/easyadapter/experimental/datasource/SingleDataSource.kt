package com.github.caoddx.easyadapter.experimental.datasource

import com.github.caoddx.easyadapter.experimental.plain.SingleGroup

interface SingleDataSource<T> {
    var group: SingleGroup<T>?

    fun haveData(): Boolean

    fun getItem(): T
}