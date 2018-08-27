package com.github.caoddx.easyadapter.experimental.dsl

import android.view.View
import com.github.caoddx.easyadapter.EasyAdapter
import com.github.caoddx.easyadapter.experimental.datasource.*

fun easyAdapter(emptyView: View? = null, adapterDsl: AdapterDsl.() -> Unit): EasyAdapter {
    val dsl = AdapterDsl()
    dsl.adapterDsl()
    return EasyAdapter(groups = *dsl.groups.toTypedArray(), emptyView = emptyView)
}

fun <T> AdapterDsl.list(data: List<T>, dsl: ListDsl<T>.() -> Unit) {
    val list = ListDsl(ImmutableListDataSource(data))
    list.dsl()
    addGroup(list.build())
}

fun <T> AdapterDsl.list(dataSource: ListDataSource<T>, dsl: ListDsl<T>.() -> Unit) {
    val list = ListDsl(dataSource)
    list.dsl()
    addGroup(list.build())
}

fun AdapterDsl.single(dsl: UnitSingleDsl.() -> Unit) {
    val single = UnitSingleDsl()
    single.dsl()
    addGroup(single.build())
}

fun <T, R : SingleDataSource<T>> AdapterDsl.single(dataSource: R, dsl: SingleDsl<T, R>.() -> Unit) {
    val single = SingleDsl(dataSource)
    single.dsl()
    addGroup(single.build())
}

fun main(args: Array<String>) {

    val ds = MutableSingleDataSource("head")
    val ds2 = MutableListDataSource<Int>()

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
