package com.github.caoddx.easyadapter.experimental.dsl

import com.github.caoddx.easyadapter.Group

class AdapterDsl {

    val groups = mutableListOf<Group>()

    internal fun addGroup(group: Group) {
        groups.add(group)
    }

    fun group(group: Group) {
        addGroup(group)
    }
}
