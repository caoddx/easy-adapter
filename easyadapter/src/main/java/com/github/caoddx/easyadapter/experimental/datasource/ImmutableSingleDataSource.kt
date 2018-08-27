package com.github.caoddx.easyadapter.experimental.datasource

import com.github.caoddx.easyadapter.experimental.plain.SingleGroup

class ImmutableSingleDataSource<T>(val data: T) : SingleDataSource<T> {

    override var group: SingleGroup<T>? = null

    var visible: Boolean = true

    override fun haveData(): Boolean {
        return visible
    }

    override fun getItem(): T {
        return data
    }

    fun show() {
        if (!visible) {
            visible = true
            group?.notifyDataSetChanged(1)
        }
    }

    fun hide() {
        if (visible) {
            visible = false
            group?.notifyDataSetChanged(0)
        }
    }
}