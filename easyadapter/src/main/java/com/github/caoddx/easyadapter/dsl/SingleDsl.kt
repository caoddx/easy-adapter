package com.github.caoddx.easyadapter.dsl

import android.view.View
import com.github.caoddx.easyadapter.Group
import com.github.caoddx.easyadapter.datasource.SingleDataSource
import com.github.caoddx.easyadapter.plain.SingleGroup

class SingleDsl<T, R : SingleDataSource<T>>(private val dataSource: R) : BaseGroupDsl() {

    private var binder: (R.(itemView: View, item: T) -> Unit)? = null

    fun bindView(binder: R.(itemView: View, item: T) -> Unit) {
        this.binder = binder
    }

    override fun build(): Group {
        return SingleGroup(layoutId, dataSource) { itemView, item ->
            binder!!.invoke(dataSource, itemView, item)
        }
    }
}