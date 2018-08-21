package com.github.caoddx.easyadapter.groupsets

import com.github.caoddx.easyadapter.Group
import com.github.caoddx.easyadapter.MultiAdapter
import com.github.caoddx.easyadapter.groups.BaseGroup

abstract class BaseGroupSet : Group, Iterable<BaseGroup<*>> {

    open var adapter: MultiAdapter? = null

    abstract val groupSize: Int

    abstract fun getGroup(index: Int): BaseGroup<*>

    abstract val itemSize: Int
}