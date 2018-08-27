package com.github.caoddx.easyadapter.experimental.datasource

class ImmutableSingleDataSource<T>(val data: T) : SingleDataSource<T> {

    override fun haveData(): Boolean {
        return data != null
    }

    override fun getItem(): T {
        return data
    }

}