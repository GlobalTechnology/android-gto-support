package org.ccci.gto.android.common.androidx.viewpager2.widget

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.whileMaintainingVisibleCurrentItem(block: (RecyclerView.Adapter<*>?) -> Unit) {
    val adapter = adapter
    val visible = adapter?.takeIf { it.hasStableIds() }?.getItemId(currentItem)
    block(adapter)
    if (visible != null) {
        (0 until adapter.itemCount).firstOrNull { adapter.getItemId(it) == visible }?.let { setCurrentItem(it, false) }
    }
}
