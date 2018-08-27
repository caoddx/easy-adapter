package com.github.caoddx.easyadapter.experimental.mix

import android.support.annotation.LayoutRes
import android.view.View
import com.github.caoddx.easyadapter.PlainGroup
import com.github.caoddx.easyadapter.experimental.datasource.ExpandableDataSource
import com.github.caoddx.easyadapter.experimental.datasource.ListDataSource
import com.github.caoddx.easyadapter.experimental.datasource.SingleDataSource
import com.github.caoddx.easyadapter.experimental.plain.ListGroup
import com.github.caoddx.easyadapter.experimental.plain.SingleGroup

class ExpandableMix<T, R>(
        private val dataSource: ExpandableDataSource<T, R>,
        @LayoutRes val headLayoutId: Int,
        val headOnBind: (itemView: View, headIndex: Int, item: T) -> Unit,
        @LayoutRes val bodyLayoutId: Int,
        val bodyOnBind: (itemView: View, item: R, position: Int) -> Unit
) : BaseMixGroup() {

    init {
        dataSource.mix = this
    }

    private val groups: MutableList<PlainGroup<*>> = dataSource.groups

    override val groupSize: Int
        get() = groups.size

    override fun getGroup(index: Int): PlainGroup<*> {
        return groups[index]
    }

    override val itemSize: Int
        get() = groups.sumBy { it.size }

    override fun iterator(): Iterator<PlainGroup<*>> {
        return groups.iterator()
    }

    fun generateHeadGroup(headIndex: Int, singleDataSource: SingleDataSource<T>): SingleGroup<T> {
        return SingleGroup(headLayoutId, singleDataSource) { itemView, item ->
            headOnBind(itemView, headIndex, item)
        }
    }

    fun generateBodyGroup(dataSource: ListDataSource<R>): ListGroup<R> {
        return ListGroup(bodyLayoutId, dataSource) { itemView, item, position ->
            bodyOnBind(itemView, item, position)
        }
    }
}
