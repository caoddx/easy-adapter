package com.github.caoddx.easyadapter.experimental

import android.support.annotation.LayoutRes
import android.view.View

class SingleGroup<T>(@LayoutRes layoutId: Int,
                     private val singleDataSource: SingleDataSource<T>,
                     val onBind: SingleGroup<T>.(itemView: View, item: T) -> Unit)
    : BaseGroup<T>(layoutId) {

    override fun bindView(itemView: View, position: Int) {
        onBind(this, itemView, getItem(position))
    }

    override val size: Int
        get() = if (singleDataSource.haveData()) 1 else 0

    override fun getItem(position: Int): T {
        require(position == 0)
        check(singleDataSource.haveData())

        return singleDataSource.getItem()
    }
}

interface SingleDataSource<T> {
    fun haveData(): Boolean

    fun getItem(): T
}

class MutableSingleDataSource<T>(initData: T? = null) : SingleDataSource<T> {

    private var data: T? = initData

    override fun haveData(): Boolean {
        return data != null
    }

    override fun getItem(): T {
        return data!!
    }

}