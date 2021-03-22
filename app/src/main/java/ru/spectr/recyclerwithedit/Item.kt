package ru.spectr.recyclerwithedit

import androidx.recyclerview.widget.DiffUtil

data class Item(
    val id: Int,
    val text: String,
    val editMode: Boolean = false,
    var isChecked: Boolean = false
) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
        }
    }
}
