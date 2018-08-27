package com.github.caoddx.easyadapter.groupsets

import com.github.caoddx.easyadapter.PlainGroup

class SingleGroupSet(private val group: PlainGroup<*>) : BaseGroupSet() {

    private val groups = listOf(group)

    init {
        group.mixGroup = this
    }

    override val groupSize: Int
        get() = 1

    override fun getGroup(index: Int): PlainGroup<*> {
        require(index == 0)
        return group
    }

    override val itemSize: Int
        get() = group.size

    override fun iterator(): Iterator<PlainGroup<*>> {
        return groups.iterator()
    }

}