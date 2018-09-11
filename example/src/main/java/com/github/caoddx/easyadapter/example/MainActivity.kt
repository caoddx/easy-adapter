package com.github.caoddx.easyadapter.example

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.github.caoddx.easyadapter.dsl.easyAdapter
import com.github.caoddx.easyadapter.dsl.list
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_action.view.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val examples = listOf(
                "no dsl example" to NoDslActivity::class.java,
                "dsl example" to DslActivity::class.java,
                "expandable example" to ExpandableActivity::class.java
        )

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = easyAdapter(empty) {

            list(examples) {
                layout(R.layout.item_action)

                bindView { itemView, (text, cls), _ ->
                    itemView.button.text = text
                    itemView.button.setOnClickListener {
                        startActivity(Intent(this@MainActivity, cls))
                    }
                }
            }
        }
    }
}
