package com.github.caoddx.easyadapter.experimental.dsl

import android.support.annotation.LayoutRes
import android.view.View
import com.github.caoddx.easyadapter.EasyAdapter
import com.github.caoddx.easyadapter.Group
import com.github.caoddx.easyadapter.experimental.*

fun easyAdapter(emptyView: View? = null, adapterDsl: AdapterDsl.() -> Unit): EasyAdapter {
    val dsl = AdapterDsl()
    dsl.adapterDsl()
    return EasyAdapter(groups = *dsl.groups.toTypedArray(), emptyView = emptyView)
}

class AdapterDsl {

    val groups = mutableListOf<Group>()

    private fun addGroup(group: Group) {
        groups.add(group)
    }

    fun <T> single(dataSource: SingleDataSource<T>, dsl: SingleDsl<T>.() -> Unit) {
        val single = SingleDsl(dataSource)
        single.dsl()
        addGroup(single.build())
    }

    fun single(dsl: UnitSingleDsl.() -> Unit) {
        val single = UnitSingleDsl()
        single.dsl()
        addGroup(single.build())
    }

    fun <T> list(dataSource: DataSource<T>, dsl: ListDsl<T>.() -> Unit) {
        val list = ListDsl(dataSource)
        list.dsl()
        addGroup(list.build())
    }

    fun <T> list(data: List<T>, dsl: ListDsl<T>.() -> Unit) {
        val list = ListDsl(ListDataSource(data))
        list.dsl()
        addGroup(list.build())
    }
}

abstract class BaseDsl {

    @LayoutRes
    protected var layoutId: Int = 0
        private set

    fun layout(@LayoutRes layoutId: Int) {
        this.layoutId = layoutId
    }

    abstract fun build(): Group
}

class ListDsl<T>(private val dataSource: DataSource<T>) : BaseDsl() {

    private var binder: (DataSource<T>.(itemView: View, item: T, position: Int) -> Unit)? = null

    fun bindView(binder: DataSource<T>. (itemView: View, item: T, position: Int) -> Unit) {
        this.binder = binder
    }

    override fun build(): Group {
        return MutableListGroup(layoutId, dataSource) { itemView, item, position ->
            binder!!.invoke(dataSource, itemView, item, position)
        }
    }
}

class UnitSingleDsl : BaseDsl() {

    private var binder: ((itemView: View) -> Unit)? = null

    fun bindView(binder: (itemView: View) -> Unit) {
        this.binder = binder
    }

    override fun build(): Group {
        return SingleGroup(layoutId, MutableSingleDataSource(Unit)) { itemView, item ->
            binder!!.invoke(itemView)
        }
    }
}

class SingleDsl<T>(private val dataSource: SingleDataSource<T>) : BaseDsl() {

    private var binder: (SingleDataSource<T>.(itemView: View, item: T) -> Unit)? = null

    fun bindView(binder: SingleDataSource<T>. (itemView: View, item: T) -> Unit) {
        this.binder = binder
    }

    override fun build(): Group {
        return SingleGroup(layoutId, dataSource) { itemView, item ->
            binder!!.invoke(dataSource, itemView, item)
        }
    }
}

fun main(args: Array<String>) {

    val ds = MutableSingleDataSource("head")
    val ds2 = MutableDataSource<Int>()

    easyAdapter {

        single(ds) {
            layout(0)

            bindView { itemView, item ->

            }
        }

        list(listOf("")) {
            layout(0)

            bindView { itemView, item, position ->

            }
        }

        list(ds2) {
            layout(0)

            bindView { itemView, item, position ->

            }
        }

        single {
            layout(0)

            bindView { itemView ->

            }
        }
    }
}