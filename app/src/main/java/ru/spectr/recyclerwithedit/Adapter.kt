package ru.spectr.recyclerwithedit

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
                    binding.cbSelect.toggle()
            }

            binding.cbSelect.setOnCheckedChangeListener { _, isChecked ->
                // Also, we can make isChecked immutable, and update it via replaceAll, and then submitList, but it looks like overengineering
                // list.replaceAll { if(it == item) it.copy(isChecked = isChecked) else it }
                getItem(adapterPosition).isChecked = isChecked
                onCheckedChange()
            }
        }

        fun bindTo(item: Item) {
            binding.cbSelect.isChecked = item.isChecked
            binding.tvText.text = item.text
            binding.cbSelect.visibility = if (item.editMode) View.VISIBLE else View.GONE
        }

        fun bindTo(item: Item, payloads: List<Any>) {
            for (payload in payloads) {
                val set = payload as Set<*>

                if (set.contains(PAYLOAD_EDIT_MODE))
                    binding.cbSelect.visibility = if (item.editMode) View.VISIBLE else View.GONE

                if (set.contains(PAYLOAD_CHECKED))
                    binding.cbSelect.isChecked = item.isChecked

                if (set.contains(PAYLOAD_TEXT))
                    binding.tvText.text = item.text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else holder.bindTo(getItem(position), payloads)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindTo(getItem(position))
}