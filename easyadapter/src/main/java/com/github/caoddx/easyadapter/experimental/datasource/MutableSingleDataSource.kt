package com.github.caoddx.easyadapter.experimental.datasource

class MutableSingleDataSource<T>(initData: T? = null) : SingleDataSource<T> {

    private var data: T? = initData

    override fun haveData(): Boolean {
        return data != null
    }

    override fun getItem(): T {
        return data!!
    }

}