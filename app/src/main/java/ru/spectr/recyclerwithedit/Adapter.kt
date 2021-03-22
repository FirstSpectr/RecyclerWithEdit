package ru.spectr.recyclerwithedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.spectr.recyclerwithedit.Item.Companion.PAYLOAD_CHECKED
import ru.spectr.recyclerwithedit.Item.Companion.PAYLOAD_EDIT_MODE
import ru.spectr.recyclerwithedit.Item.Companion.PAYLOAD_TEXT
import ru.spectr.recyclerwithedit.databinding.ItemBinding

class Adapter : ListAdapter<Item, Adapter.ViewHolder>(Item.DIFF) {
    var onCheckedChange: () -> Unit = {}

    inner class ViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (getItem(adapterPosition).editMode)
                    binding.cbSelect.isChecked = !binding.cbSelect.isChecked
            }

            binding.cbSelect.setOnCheckedChangeListener { _, isChecked ->
                getItem(adapterPosition).isChecked = isChecked
                onCheckedChange()
            }
        }

        fun bindTo(item: Item) {
            binding.cbSelect.isChecked = item.isChecked
            binding.tvText.text = item.text
            binding.cbSelect.visibility = if (item.editMode) View.VISIBLE else View.GONE
        }

        fun bindPayload(payload: Bundle) {
            if (payload.containsKey(PAYLOAD_EDIT_MODE))
                binding.cbSelect.visibility = if (payload.getBoolean(PAYLOAD_EDIT_MODE)) View.VISIBLE else View.GONE

            if (payload.containsKey(PAYLOAD_CHECKED))
                binding.cbSelect.isChecked = payload.getBoolean(PAYLOAD_CHECKED)

            if (payload.containsKey(PAYLOAD_TEXT))
                binding.tvText.text = payload.getString(PAYLOAD_TEXT)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else holder.bindPayload(payloads[0] as Bundle)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindTo(getItem(position))
}