package com.hatem.noureddine.weatherapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


/**
 * ItemDecoration to add space between recycler items, support all kind of [RecyclerView.LayoutManager]
 * @property startSpace Int : Space to add on the top (HORIZONTAL) or on the start (VERTICAL) of the item
 * @property endSpace Int : Space to add on the right/end of the item
 * @property bottomSpace Int : Space to add on the bottom of the item
 * @property headerSpace Int : Space to add on the start (HORIZONTAL) or on the top (VERTICAL) of the item
 * @property footerSpace Int : Space to add on the end (HORIZONTAL) or on the bottom (VERTICAL) of the item
 * @constructor
 */
class SpaceItemDecoration(
    private val startSpace: Int = 0,
    private val endSpace: Int = 0,
    private val bottomSpace: Int = 0,
    private val headerSpace: Int = 0,
    private val footerSpace: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val orientation = when (val layout = parent.layoutManager) {
            is LinearLayoutManager -> layout.orientation
            is StaggeredGridLayoutManager -> layout.orientation
            is GridLayoutManager -> layout.orientation
            else -> LinearLayoutManager.VERTICAL
        }

        when (orientation) {
            LinearLayoutManager.HORIZONTAL -> when (parent.getChildAdapterPosition(view)) {
                0 -> {
                    outRect.left = headerSpace
                    outRect.top = startSpace
                    outRect.right = endSpace
                    outRect.bottom = bottomSpace
                }

                (parent.adapter?.itemCount ?: 0) - 1 -> {
                    outRect.top = startSpace
                    outRect.bottom = bottomSpace
                    if (footerSpace > 0) {
                        outRect.right = footerSpace
                    }
                }
                else -> {
                    outRect.top = startSpace
                    outRect.right = endSpace
                    outRect.bottom = bottomSpace
                }
            }

            else -> when (parent.getChildAdapterPosition(view)) {
                0 -> {
                    outRect.left = startSpace
                    outRect.top = headerSpace
                    outRect.right = endSpace
                    outRect.bottom = bottomSpace
                }
                (parent.adapter?.itemCount ?: 0) - 1 -> {
                    outRect.left = startSpace
                    outRect.right = endSpace
                    if (footerSpace > 0) {
                        outRect.bottom = footerSpace
                    }
                }
                else -> {
                    outRect.left = startSpace
                    outRect.right = endSpace
                    outRect.bottom = bottomSpace
                }
            }
        }
    }
}

