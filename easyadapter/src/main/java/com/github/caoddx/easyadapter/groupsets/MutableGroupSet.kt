package com.github.caoddx.easyadapter.groupsets

import com.github.caoddx.easyadapter.groups.BaseGroup
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

    fun addLast(group: BaseGroup<*>) {
        groups.addLast(group)
        group.groupSet = this
        val p = adapter?.getGroupStartPosition(group)
        adapter?.notifyItemRangeInserted(p!!, group.size)
    }

    fun remoteAt(index: Int): BaseGroup<*> {

        val group = groups[index]

        val p = adapter?.getGroupStartPosition(group)

        groups.removeAt(index)
        group.groupSet = null

        adapter?.notifyItemRangeRemoved(p!!, group.size)

        return group
    }

}