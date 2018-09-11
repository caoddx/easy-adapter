package com.github.caoddx.easyadapter.datasource

import com.github.caoddx.easyadapter.plain.SingleGroup

class MutableSingleDataSource<T>(initData: T) : SingleDataSource<T> {

    override var group: SingleGroup<T>? = null

    var data: T = initData
        set(value) {
            field = value
            if (visible) {
                group?.notifyDataSetChanged()
            }
        }

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
            group?.notifyDataSetChanged(0)
        }
    }

    fun hide() {
        if (visible) {
            visible = false
            group?.notifyDataSetChanged(1)
        }
    }
}