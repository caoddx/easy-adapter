package com.github.caoddx.easyadapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.caoddx.easyadapter.mix.SingleGroupSet

/*var RecyclerView.multiAdapter: EasyAdapter
    set(value) {
        adapter = value
    }
    get() = adapter as EasyAdapter*/

class EasyAdapter(vararg groups: Group, private val emptyView: View? = null) : RecyclerView.Adapter<EasyAdapter.ViewHolder>() {

    private val groupSets: List<MixGroup> = groups.map {
        (it as? MixGroup ?: SingleGroupSet(it as PlainGroup<*>)).also { it.adapter = this }
    }

    private fun transSetPosition(packageGroup: MixGroup, position: Int): Pair<PlainGroup<*>, Int> {
        var index = 0
        for (g in packageGroup) {
            val p = position - index
            if (p < g.size) {
                return g to p
            }
            index += g.size
        }
        throw IllegalArgumentException("group not found")
    }

    private fun transPosition(position: Int): Pair<PlainGroup<*>, Int> {
        var index = 0
        for (pg in groupSets) {
            val p = position - index
            val itemSize = pg.itemSize
            if (p < itemSize) {
                return transSetPosition(pg, p)
            }
            index += itemSize
        }
        throw IllegalArgumentException("set not found")
    }

    internal fun getGroupStartPosition(group: PlainGroup<*>): Int {
        var p = 0
        for (pg in groupSets) {
            for (g in pg) {
                if (g === group) {
                    return p
                }
                p += g.size
            }
        }
        throw IllegalArgumentException("group not found")
    }

    override fun getItemViewType(position: Int): Int {
        return transPosition(position).first.layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, resId: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(resId, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groupSets.sumBy { it.itemSize }.also {
            emptyView?.visibility = if (it == 0) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (g, p) = transPosition(position)
        g.bindView(holder.itemView, p)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}