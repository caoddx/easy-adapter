package com.github.caoddx.easyadapter.mix

import com.github.caoddx.easyadapter.plain.BaseGroup
import java.util.*

open class MutableGroupSet : BaseGroupSet() {

    private val groups = LinkedList<BaseGroup<*>>()

    override val groupSize: Int
        get() = groups.size

    override fun getGroup(index: Int): BaseGroup<*> {
        return groups[index]
    }

    override val itemSize: Int
        get() = groups.sumBy { it.size }

    override fun iterator(): Iterator<BaseGroup<*>> {
        return groups.iterator()
    }

    fun clear() {
        if (groups.isEmpty()) return

        val p = adapter?.getGroupStartPosition(groups.first)
        val size = itemSize
        groups.clear()
        adapter?.notifyItemRangeRemoved(p!!, size)
    }

    fun add(index: Int, group: BaseGroup<*>) {
        groups.add(index, group)
        group.mixGroup = this
        val p = adapter?.getGroupStartPosition(group)
        adapter?.notifyItemRangeInserted(p!!, group.size)
    }

    fun addLast(group: BaseGroup<*>) {
        groups.addLast(group)
        group.mixGroup = this
        val p = adapter?.getGroupStartPosition(group)
        adapter?.notifyItemRangeInserted(p!!, group.size)
    }

    fun remoteAt(index: Int): BaseGroup<*> {

        val group = groups[index]

        val p = adapter?.getGroupStartPosition(group)

        groups.removeAt(index)
        group.mixGroup = null

        adapter?.notifyItemRangeRemoved(p!!, group.size)

        return group
    }

    fun replaceAt(index: Int, newGroup: BaseGroup<*>) {
        remoteAt(index)
        add(index, newGroup)
    }
}