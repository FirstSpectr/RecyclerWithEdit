package ru.spectr.recyclerwithedit

import android.os.Bundle
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
                val bundle = Bundle()

                if (oldItem.editMode != newItem.editMode)
                    bundle.putBoolean(PAYLOAD_EDIT_MODE, newItem.editMode)

                if (oldItem.isChecked != newItem.isChecked)
                    bundle.putBoolean(PAYLOAD_CHECKED, newItem.isChecked)

                if (oldItem.text != newItem.text)
                    bundle.putString(PAYLOAD_TEXT, newItem.text)

                if (!bundle.isEmpty)
                    return bundle

                return super.getChangePayload(oldItem, newItem)
            }
        }

        const val PAYLOAD_EDIT_MODE = "PAYLOAD_EDIT_MODE"
        const val PAYLOAD_CHECKED = "PAYLOAD_CHECKED"
        const val PAYLOAD_TEXT = "PAYLOAD_TEXT"
    }
}
