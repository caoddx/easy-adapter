package com.github.caoddx.easyadapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.caoddx.easyadapter.groups.BaseGroup
import com.github.caoddx.easyadapter.groupsets.BaseGroupSet
import com.github.caoddx.easyadapter.groupsets.SingleGroupSet

/*var RecyclerView.multiAdapter: MultiAdapter
    set(value) {
        adapter = value
    }
    get() = adapter as MultiAdapter*/

class MultiAdapter(vararg groups: Group) : RecyclerView.Adapter<MultiAdapter.ViewHolder>() {

    private val groupSets: List<BaseGroupSet> = groups.map {
        (it as? BaseGroupSet ?: SingleGroupSet(it as BaseGroup<*>))
                .also { it.adapter = this }
    }

    private fun transSetPosition(packageGroup: BaseGroupSet, position: Int): Pair<BaseGroup<*>, Int> {
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

    private fun transPosition(position: Int): Pair<BaseGroup<*>, Int> {
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

    internal fun getGroupStartPosition(group: BaseGroup<*>): Int {
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
        return groupSets.sumBy { it.itemSize }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (g, p) = transPosition(position)
        g.postOnBind(holder.itemView, p)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}