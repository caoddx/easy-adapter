package com.github.caoddx.easyadapter.experimental.plain

import android.support.annotation.LayoutRes
import android.view.View
import com.github.caoddx.easyadapter.experimental.datasource.SingleDataSource

class SingleGroup<T>(@LayoutRes layoutId: Int,
                     private val singleDataSource: SingleDataSource<T>,
                     val onBind: SingleGroup<T>.(itemView: View, item: T) -> Unit)
    : BasePlainGroup<T>(layoutId) {

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
