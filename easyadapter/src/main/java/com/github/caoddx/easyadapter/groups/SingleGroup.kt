package com.github.caoddx.easyadapter.groups

import android.support.annotation.LayoutRes
import android.view.View
import com.github.caoddx.easyadapter.groups.BaseGroup

open class SingleGroup(@LayoutRes layoutId: Int, initVisible: Boolean = true, onBind: BaseGroup<Unit>.(itemView: View, position: Int) -> Unit) : BaseGroup<Unit>(layoutId, onBind) {

    override fun getItem(position: Int) {}

    override val size: Int
        get() = if (_visible) 1 else 0

    private var _visible: Boolean = initVisible

    var visible: Boolean
        get() = _visible
        set(value) {
            if (value != _visible) {
                val oldSize = size
                _visible = value
                notifyDataSetChanged(oldSize)
            }
        }

    fun show() {
        visible = true
    }

    fun hide() {
        visible = false
    }
}