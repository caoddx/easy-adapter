package com.github.caoddx.easyadapter.dsl

import android.support.annotation.LayoutRes
import com.github.caoddx.easyadapter.Group

abstract class BaseGroupDsl {

    @LayoutRes
    internal var layoutId: Int = 0
        private set

    fun layout(@LayoutRes layoutId: Int) {
        this.layoutId = layoutId
    }

    abstract fun build(): Group
}