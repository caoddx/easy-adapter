package com.github.caoddx.easyadapter.groupsets

import android.support.annotation.LayoutRes
import android.view.View
import com.github.caoddx.easyadapter.EasyAdapter
import com.github.caoddx.easyadapter.groups.BaseGroup
import com.github.caoddx.easyadapter.groups.MutableGroup
import com.github.caoddx.easyadapter.groups.SingleGroup
import java.util.*

class ExpandableGroupSet<T, R>(@LayoutRes val headLayoutId: Int, val headOnBind: ExpandableGroupSet<T, R>.(group: BaseGroup<T>, headIndex: Int, itemView: View) -> Unit,
                               @LayoutRes val layoutId: Int, val onBind: BaseGroup<R>.(itemView: View, position: Int) -> Unit) : BaseGroupSet() {

    private val heads = LinkedList<Head<T, R>>()
    private val inner = MutableGroupSet()

    override var adapter: EasyAdapter?
        get() = super.adapter
        set(value) {
            super.adapter = value
            inner.adapter = value
        }

    override val groupSize: Int
        get() = inner.groupSize

    override fun getGroup(index: Int): BaseGroup<*> {
        return inner.getGroup(index)
    }

    override val itemSize: Int
        get() = inner.itemSize

    override fun iterator(): Iterator<BaseGroup<*>> {
        return inner.iterator()
    }

    fun add(head: T, bodies: List<R>) {
        val index = heads.size
        heads.addLast(Head(head, index, bodies))

        val headGroup = SingleGroup(headLayoutId, head, true) { itemView, position ->
            headOnBind(this, index, itemView)
        }
        val bodyGroup = MutableGroup<R>(layoutId) { itemView, position ->
            onBind(itemView, position)
        }
        inner.addLast(headGroup)
        inner.addLast(bodyGroup)
    }

    fun isFolded(index: Int): Boolean {
        val head = heads[index]
        return head.folded
    }

    fun getBodies(index: Int): List<R> {
        val head = heads[index]
        return head.bodies
    }

    fun getBodySize(index: Int): Int {
        return getBodies(index).size
    }

    fun fold(index: Int) {
        val head = heads[index]
        @Suppress("UNCHECKED_CAST")
        val g = inner.getGroup(index * 2 + 1) as MutableGroup<R>
        if (!head.folded) {
            head.folded = true
            g.clear()
        }
    }

    fun unfold(index: Int) {
        val head = heads[index]
        @Suppress("UNCHECKED_CAST")
        val g = inner.getGroup(index * 2 + 1) as MutableGroup<R>
        if (head.folded) {
            head.folded = false
            g.replace(head.bodies)
        }
    }

    fun toggle(index: Int) {
        val head = heads[index]
        if (head.folded) {
            unfold(index)
        } else {
            fold(index)
        }
    }

    fun foldAll() {
        heads.forEachIndexed { index, _ ->
            fold(index)
        }
    }

    fun unfoldAll() {
        heads.forEachIndexed { index, _ ->
            unfold(index)
        }
    }

    private class Head<T, R>(val head: T, val headIndex: Int, val bodies: List<R>) {
        var folded: Boolean = true
    }
}
