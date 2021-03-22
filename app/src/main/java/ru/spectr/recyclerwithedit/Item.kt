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

            override fun getChangePayload(oldItem: Item, newItem: Item): Any? {
                val payload = mutableSetOf<Int>()

                if (oldItem.editMode != newItem.editMode)
                    payload.add(PAYLOAD_EDIT_MODE)

                if (oldItem.isChecked != newItem.isChecked)
                    payload.add(PAYLOAD_CHECKED)

                if (oldItem.text != newItem.text)
                    payload.add(PAYLOAD_TEXT)

                return if (payload.isNotEmpty()) payload
                else super.getChangePayload(oldItem, newItem)
            }
        }

        const val PAYLOAD_EDIT_MODE = 0
        const val PAYLOAD_CHECKED = 1
        const val PAYLOAD_TEXT = 2
    }
}
