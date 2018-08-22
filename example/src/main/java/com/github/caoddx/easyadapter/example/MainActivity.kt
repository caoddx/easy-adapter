package com.github.caoddx.easyadapter.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.github.caoddx.easyadapter.EasyAdapter
import com.github.caoddx.easyadapter.groupsets.ExpandableGroupSet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_body.view.*
import kotlinx.android.synthetic.main.item_head.view.*
import java.util.concurrent.ThreadLocalRandom


class MainActivity : AppCompatActivity() {

    private val group = ExpandableGroupSet<String, String>(
            R.layout.item_head,
            { group, headIndex, itemView ->
                itemView.textViewHead.text = group.getItem(0)
                itemView.setOnClickListener {
                    toggle(headIndex)
                }
            },
            R.layout.item_body,
            { itemView, position ->
                itemView.textViewIndex.text = position.toString()
                itemView.textViewContent.text = getItem(position)
            })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val random = ThreadLocalRandom.current()
        (0..99).forEach {
            val head = "head$it"
            val list = (0..random.nextInt(0, 100)).map { "$head body$it" }
            group.add(head, list)
        }

        recyclerView.adapter = EasyAdapter(emptyView = empty)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}
