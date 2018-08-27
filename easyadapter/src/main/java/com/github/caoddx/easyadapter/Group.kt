package com.github.caoddx.easyadapter

import android.view.View

interface Group

interface PlainGroup<T> : Group {

    var mixGroup: MixGroup?

    val adapter: EasyAdapter? get() = mixGroup?.adapter

    val size: Int

    fun getItem(position: Int): T

    val layoutId: Int

    fun bindView(itemView: View, position: Int)
}

interface MixGroup : Group, Iterable<PlainGroup<*>> {

    var adapter: EasyAdapter?

    val groupSize: Int

    fun getGroup(index: Int): PlainGroup<*>

    val itemSize: Int
}