package com.github.caoddx.easyadapter.datasource

import com.github.caoddx.easyadapter.PlainGroup
import com.github.caoddx.easyadapter.mix.ExpandableMix
import java.util.*

class ExpandableDataSource<T, R>(initData: List<Pair<T, List<R>>>) {

    fun getSize(): Int {
        return data.size
    }

    fun getItem(position: Int): Pair<T, List<R>> {
        return data[position]
    }

    private val data: MutableList<Pair<T, List<R>>> = LinkedList(initData)

    internal var mix: ExpandableMix<T, R>? = null
        set(value) {
            field = value

            if (value != null) {
                data.forEachIndexed { index, (head, bodies) ->
                    add(index, head, bodies, false)
                }
            }
        }

    internal val groups: MutableList<PlainGroup<*>> = LinkedList()

    private fun addGroup(group: PlainGroup<*>) {
        group.mixGroup = mix
        groups.add(group)
    }

    private fun add(index: Int, head: T, bodies: List<R>, notify: Boolean) {
        val m = mix
        if (m != null) {
            val hg = m.generateHeadGroup(index, ImmutableSingleDataSource(head))
            val bg = m.generateBodyGroup(ImmutableListDataSource(bodies))
            addGroup(hg)
            addGroup(bg)

            val adapter = m.adapter
            if (notify && adapter != null) {
                adapter.notifyItemRangeInserted(adapter.getGroupStartPosition(hg), hg.size)
                adapter.notifyItemRangeInserted(adapter.getGroupStartPosition(bg), bg.size)
            }
        }
    }

    fun add(head: T, bodies: List<R>) {
        val index = data.size
        data.add(head to bodies)
        add(index, head, bodies, true)
    }

    data class Head<T, R>(val headIndex: Int, val head: T, val bodies: List<R>, val folded: Boolean)
}