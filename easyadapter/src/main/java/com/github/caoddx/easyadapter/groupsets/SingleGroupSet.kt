package com.github.caoddx.easyadapter.groupsets

import com.github.caoddx.easyadapter.groups.BaseGroup

class SingleGroupSet(private val group: BaseGroup<*>) : BaseGroupSet() {

    private val groups = listOf(group)

    init {
        group.groupSet = this
    }

    override val groupSize: Int
        get() = 1

    override fun getGroup(index: Int): BaseGroup<*> {
        require(index == 0)
        return group
    }

    override val itemSize: Int
        get() = group.size

    override fun iterator(): Iterator<BaseGroup<*>> {
        return groups.iterator()
    }

}