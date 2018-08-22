package com.github.caoddx.easyadapter.groupsets

import com.github.caoddx.easyadapter.Group
import com.github.caoddx.easyadapter.EasyAdapter
import com.github.caoddx.easyadapter.groups.BaseGroup

abstract class BaseGroupSet : Group, Iterable<BaseGroup<*>> {

    open var adapter: EasyAdapter? = null

    abstract val groupSize: Int

    abstract fun getGroup(index: Int): BaseGroup<*>

    abstract val itemSize: Int
}