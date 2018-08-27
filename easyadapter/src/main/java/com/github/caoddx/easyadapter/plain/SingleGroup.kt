package com.github.caoddx.easyadapter.plain

import android.support.annotation.LayoutRes
import android.view.View

open class SingleGroup<T>(@LayoutRes layoutId: Int, item: T, initVisible: Boolean = true,
                          onBind: BaseGroup<T>.(itemView: View, position: Int) -> Unit) : BaseGroup<T>(layoutId, onBind) {

    var item: T = item
        private set

    override fun getItem(position: Int): T {
        return item
    }

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

    fun replace(item: T) {
        this.item = item
        if (visible) {
            notifyItemChanged(0)
        }
    }
}