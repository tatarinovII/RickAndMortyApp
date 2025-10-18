package com.my.rickandmortyapp.activity.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.my.domain.models.Character
import com.my.rickandmortyapp.R
import com.my.rickandmortyapp.viewmodel.MainViewModel


class CharacterAdapter(
    private val onItemClick: (character: Character) -> Unit,
    private val onLoadMore: () -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {

    private var list: List<Character> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(item, onItemClick)
    }

    override fun onBindViewHolder(
        holder: CharacterViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
        if (position >= list.size - 5 && list.isNotEmpty()) {
            onLoadMore()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateItems(newItems: List<Character>) {
        val diffCallback = CharacterDiffCallback(this.list, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    private class CharacterDiffCallback(
        private val oldList: List<Character>,
        private val newList: List<Character>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

}