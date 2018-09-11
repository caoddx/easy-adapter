package com.github.caoddx.easyadapter.dsl

import android.view.View
import com.github.caoddx.easyadapter.Group
import com.github.caoddx.easyadapter.datasource.ExpandableDataSource
import com.github.caoddx.easyadapter.mix.ExpandableMix

class ExpandableDsl<T, R, D : ExpandableDataSource<T, R>>(private val dataSource: D) : BaseGroupDsl() {

    internal val head = ExpandableDsl.HeadDsl<T>()

    internal val bodies = BodiesDsl<R>()

    override fun build(): Group {
        return ExpandableMix(
                dataSource,
                head.layoutId,
                { itemView, headIndex, item ->
                    head.binder(itemView, headIndex, item)
                },
                bodies.layoutId,
                { itemView, item, position ->
                    bodies.binder(itemView, item, position)
                }
        )
    }

    class HeadDsl<T> : BaseGroupDsl() {

        internal lateinit var binder: (itemView: View, headIndex: Int, item: T) -> Unit

        fun bindView(binder: (itemView: View, headIndex: Int, item: T) -> Unit) {
            this.binder = binder
        }

        override fun build(): Group {
            throw UnsupportedOperationException()
        }
    }

    class BodiesDsl<T> : BaseGroupDsl() {

        internal lateinit var binder: (itemView: View, item: T, position: Int) -> Unit

        fun bindView(binder: (itemView: View, item: T, position: Int) -> Unit) {
            this.binder = binder
        }

        override fun build(): Group {
            throw UnsupportedOperationException()
        }
    }
}