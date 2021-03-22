package ru.spectr.recyclerwithedit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindTo(getItem(position))
}