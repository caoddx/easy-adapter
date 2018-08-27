package com.github.caoddx.easyadapter.experimental.dsl

import android.view.View
import com.github.caoddx.easyadapter.Group
import com.github.caoddx.easyadapter.experimental.datasource.ExpandableDataSource
import com.github.caoddx.easyadapter.experimental.datasource.ListDataSource
import com.github.caoddx.easyadapter.experimental.datasource.SingleDataSource

class ExpandableDsl<T, R, D : ExpandableDataSource<T, R>>(private val dataSource: D) : BaseGroupDsl() {

    internal val head = ExpandableDsl.HeadDsl<T>()

    internal val bodies = BodiesDsl<T>()

    override fun build(): Group {
        dataSource
        head.layoutId
        head.binder
        bodies.layoutId
        bodies.binder

        TODO("not implemented")
    }

    class HeadDsl<T> : BaseGroupDsl() {

        internal lateinit var binder: (SingleDataSource<T>.(itemView: View, item: T) -> Unit)

        fun bindView(binder: SingleDataSource<T>.(itemView: View, item: T) -> Unit) {
            this.binder = binder
        }

        override fun build(): Group {
            throw UnsupportedOperationException()
        }
    }

    class BodiesDsl<T> : BaseGroupDsl() {

        internal lateinit var binder: (ListDataSource<T>.(itemView: View, item: T, position: Int) -> Unit)

        fun bindView(binder: ListDataSource<T>.(itemView: View, item: T, position: Int) -> Unit) {
            this.binder = binder
        }

        override fun build(): Group {
            throw UnsupportedOperationException()
        }
    }
}