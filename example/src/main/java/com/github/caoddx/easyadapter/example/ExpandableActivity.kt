package com.github.caoddx.easyadapter.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.github.caoddx.easyadapter.datasource.ExpandableDataSource
import com.github.caoddx.easyadapter.dsl.*
import kotlinx.android.synthetic.main.activity_expandable.*
import kotlinx.android.synthetic.main.item_action.view.*
import kotlinx.android.synthetic.main.item_body.view.*

class ExpandableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandable)

        val ds = ExpandableDataSource(listOf(
                "head1" to listOf(0, 1, 2),
                "head2" to listOf(0, 1, 2),
                "head3" to listOf(0, 1, 2),
                "head4" to listOf(0, 1, 2),
                "head5" to listOf(0, 1, 2)
        ))

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = easyAdapter(empty) {

            single {
                layout(R.layout.item_action)

                bindView { itemView ->
                    itemView.button.text = "add one"
                    itemView.button.setOnClickListener {
                        ds.add("new head ${ds.getSize()}", listOf(0, 0, 0, 0, 0, 0))
                    }
                }
            }

            expandable(ds) {

                head {
                    layout(R.layout.item_action)

                    bindView { itemView, headIndex, item ->
                        itemView.button.text = "$headIndex $item"
                        itemView.button.setOnClickListener {
                            ds.toggle(headIndex)
                        }
                    }
                }

                bodies {
                    layout(R.layout.item_body)

                    bindView { itemView, item, position ->
                        itemView.textViewIndex.text = position.toString()
                        itemView.textViewContent.text = item.toString()
                    }
                }
            }
        }
    }
}
