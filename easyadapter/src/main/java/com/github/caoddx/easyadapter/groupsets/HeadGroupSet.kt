package com.github.caoddx.easyadapter.groupsets

import com.github.caoddx.easyadapter.groups.BaseGroup
import com.github.caoddx.easyadapter.groups.SingleGroup

class HeadGroupSet(private val headGroup: SingleGroup<*>, private val dataGroup: BaseGroup<*>, private val footGroup: SingleGroup<*>? = null) : BaseGroupSet() {

    private val groups =
            if (footGroup == null)
                listOf(headGroup, dataGroup)
            else
                listOf(headGroup, dataGroup, footGroup)

    init {
        dataGroup.addOnDataSetChangedListener {
            headGroup.visible = it.size != 0
            footGroup?.visible = it.size != 0
        }
        groups.forEach { it.mixGroup = this }
    }

    override val groupSize: Int = groups.size

    override fun getGroup(index: Int): BaseGroup<*> {
        return groups[index]
    }

    override val itemSize: Int
        get() = groups.sumBy { it.size }

    override fun iterator(): Iterator<BaseGroup<*>> {
        return groups.iterator()
    }
}