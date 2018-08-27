package com.github.caoddx.easyadapter.plain

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.annotation.LayoutRes
import android.view.View

class LiveDataGroup<T>(@LayoutRes layoutId: Int, owner: LifecycleOwner, liveData: LiveData<List<T>>, onBind: BaseGroup<T>.(itemView: View, position: Int) -> Unit) : BaseGroup<T>(layoutId, onBind) {

    private var items: List<T> = emptyList()

    init {
        liveData.observe(owner, Observer {
            val oldSize = size
            items = it ?: emptyList()
            notifyDataSetChanged(oldSize)
        })
    }

    override fun getItem(position: Int): T {
        return items[position]
    }

    override val size: Int
        get() = items.size

}