package ru.spectr.recyclerwithedit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import ru.spectr.recyclerwithedit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.adapter = adapter
        binding.rvList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter.submitList(generateList())

        binding.tvEdit.setOnClickListener {
            val editMode = binding.tvEdit.text == getString(R.string.edit)

            setEditModeEnabled(editMode)

            val newList = adapter.currentList.toMutableList()

            if (editMode) newList.replaceAll { it.copy(editMode = true) }
            else newList.replaceAll { it.copy(editMode = false, isChecked = false) }

            adapter.submitList(newList)
        }

        binding.btDelete.setOnClickListener {
            setEditModeEnabled(false)

            val newList = adapter.currentList.toMutableList()
            newList.removeIf { it.isChecked }
            newList.replaceAll { it.copy(editMode = false) }
            adapter.submitList(newList)
        }

        binding.tvSelectAll.setOnClickListener {
            val newList = adapter.currentList.toMutableList()
            newList.replaceAll { it.copy(isChecked = true) }
            adapter.submitList(newList)
        }

        adapter.onCheckedChange = {
            binding.btDelete.isEnabled = adapter.currentList.any { it.isChecked }
        }
    }

    private fun setEditModeEnabled(editMode: Boolean) {
        binding.tvEdit.text = if (editMode) getString(R.string.done) else getString(R.string.edit)
        binding.btDelete.visibility = if (editMode) View.VISIBLE else View.GONE
        binding.btDelete.isEnabled = false
        binding.tvSelectAll.visibility = if (editMode) View.VISIBLE else View.GONE
    }


    private fun generateList(n: Int = 30) = List(n) { Item(it, "ITEM $it") }
}