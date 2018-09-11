package com.github.caoddx.easyadapter.dsl

import android.view.View
import com.github.caoddx.easyadapter.Group
import com.github.caoddx.easyadapter.datasource.ImmutableSingleDataSource
import com.github.caoddx.easyadapter.plain.SingleGroup

class UnitSingleDsl : BaseGroupDsl() {

    private var binder: ((itemView: View) -> Unit)? = null

    fun bindView(binder: (itemView: View) -> Unit) {
        this.binder = binder
    }

    override fun build(): Group {
        return SingleGroup(layoutId, ImmutableSingleDataSource(Unit)) { itemView, item ->
            binder!!.invoke(itemView)
        }
    }
}