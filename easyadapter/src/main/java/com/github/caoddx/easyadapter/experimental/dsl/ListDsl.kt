package com.github.caoddx.easyadapter.experimental.dsl

import android.view.View
import com.github.caoddx.easyadapter.Group
import com.github.caoddx.easyadapter.experimental.datasource.ListDataSource
import com.github.caoddx.easyadapter.experimental.plain.ListGroup

class ListDsl<T>(private val dataSource: ListDataSource<T>) : BaseGroupDsl() {

    private var binder: (ListDataSource<T>.(itemView: View, item: T, position: Int) -> Unit)? = null

    fun bindView(binder: ListDataSource<T>.(itemView: View, item: T, position: Int) -> Unit) {
        this.binder = binder
    }

    override fun build(): Group {
        return ListGroup(layoutId, dataSource) { itemView, item, position ->
            binder!!.invoke(dataSource, itemView, item, position)
        }
    }
}