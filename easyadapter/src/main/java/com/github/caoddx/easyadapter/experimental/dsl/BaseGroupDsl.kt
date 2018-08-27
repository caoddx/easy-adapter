package com.github.caoddx.easyadapter.experimental.dsl

import android.support.annotation.LayoutRes
import com.github.caoddx.easyadapter.Group

abstract class BaseGroupDsl {

    @LayoutRes
    protected var layoutId: Int = 0
        private set

    fun layout(@LayoutRes layoutId: Int) {
        this.layoutId = layoutId
    }

    abstract fun build(): Group
}