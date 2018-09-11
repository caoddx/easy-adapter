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
        set(value) {
            if (field && !value) {
                field = value
                group?.notifyDataSetChanged(1)
            }
            if (!field && value) {
                field = value
                group?.notifyDataSetChanged(0)
            }
        }

    override fun haveData(): Boolean {
        return visible
    }

    override fun getItem(): T {
        return data
    }

    fun show() {
        visible = true
    }

    fun hide() {
        visible = false
    }
}