package com.github.caoddx.easyadapter.experimental.datasource

import com.github.caoddx.easyadapter.PlainGroup
import com.github.caoddx.easyadapter.experimental.mix.ExpandableMix
import java.util.*

class ExpandableDataSource<T, R>(initData: List<Pair<T, List<R>>>) {

    private val data: MutableList<Pair<T, List<R>>> = LinkedList(initData)

    internal var group: ExpandableMix<T, R>? = null
        set(value) {
            field = value

            if (value != null) {
                data.forEachIndexed { index, (head, body) ->
                    val hg = value.generateHeadGroup(index, ImmutableSingleDataSource(head))
                    val bg = value.generateBodyGroup(ImmutableListDataSource(body))
                    groups.add(hg)
                    groups.add(bg)
                }
            }
        }

    internal val groups: MutableList<PlainGroup<*>> = LinkedList()

    data class Head<T, R>(val headIndex: Int, val head: T, val bodies: List<R>, val folded: Boolean)
}