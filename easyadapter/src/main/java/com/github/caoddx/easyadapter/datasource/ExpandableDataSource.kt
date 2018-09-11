package com.github.caoddx.easyadapter.datasource

import com.github.caoddx.easyadapter.PlainGroup
import com.github.caoddx.easyadapter.mix.ExpandableMix
import java.util.*

class ExpandableDataSource<T, R>(initData: List<Pair<T, List<R>>>) {

    fun getSize(): Int {
        return data.size
    }

    fun getItem(position: Int): Item<T, R> {
        return data[position]
    }

    private val data: MutableList<Item<T, R>> = LinkedList(initData.map {
        Item(it.first, it.second, false, ImmutableSingleDataSource(it.first), MutableListDataSource(it.second))
    })

    internal var mix: ExpandableMix<T, R>? = null
        set(value) {
            field = value

            if (value != null) {
                data.forEachIndexed { index, item ->
                    add(index, item, false)
                }
            }
        }

    internal val groups: MutableList<PlainGroup<*>> = LinkedList()

    private fun addGroup(group: PlainGroup<*>) {
        group.mixGroup = mix
        groups.add(group)
    }

    private fun add(index: Int, item: Item<T, R>, notify: Boolean) {
        val m = mix
        if (m != null) {
            val hg = m.generateHeadGroup(index, item.headDataSource)
            val bg = m.generateBodyGroup(item.bodyDataSource)
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
        val item = Item(head, bodies, false, ImmutableSingleDataSource(head), MutableListDataSource(bodies))
        data.add(item)
        add(index, item, true)
    }

    fun fold(headIndex: Int) {
        val item = data[headIndex]
        if (!item.folded) {
            item.bodyDataSource.clear()
            data[headIndex] = item.copy(folded = true)
        }
    }

    fun unfold(headIndex: Int) {
        val item = data[headIndex]
        if (item.folded) {
            item.bodyDataSource.replace(item.bodies)
            data[headIndex] = item.copy(folded = false)
        }
    }

    fun toggle(headIndex: Int) {
        val item = data[headIndex]
        if (item.folded) {
            unfold(headIndex)
        } else {
            fold(headIndex)
        }
    }

    data class Item<T, R>(val head: T, val bodies: List<R>, val folded: Boolean,
                          internal val headDataSource: ImmutableSingleDataSource<T>,
                          internal val bodyDataSource: MutableListDataSource<R>)
}