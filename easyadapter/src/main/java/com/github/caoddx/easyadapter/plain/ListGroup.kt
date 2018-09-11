package com.github.caoddx.easyadapter.plain

import android.support.annotation.LayoutRes
import android.view.View
import com.github.caoddx.easyadapter.datasource.ListDataSource

class ListGroup<T>(@LayoutRes layoutId: Int,
                   val dataSource: ListDataSource<T>,
                   val onBind: ListGroup<T>.(itemView: View, item: T, position: Int) -> Unit)
    : BasePlainGroup<T>(layoutId) {

    init {
        dataSource.group = this
    }

    override fun bindView(itemView: View, position: Int) {
        onBind(this, itemView, getItem(position), position)
    }

    override val size: Int
        get() = dataSource.getSize()

    override fun getItem(position: Int): T {
        return dataSource.getItem(position)
    }
}