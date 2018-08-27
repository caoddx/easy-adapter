package com.github.caoddx.easyadapter.experimental.datasource

interface SingleDataSource<T> {
    fun haveData(): Boolean

    fun getItem(): T
}