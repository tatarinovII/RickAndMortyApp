package com.my.rickandmortyapp.activity.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.domain.models.Character
import com.my.rickandmortyapp.R


class CharacterAdapter(
    private val list: MutableList<Character>,
    private val onItemClick: (character: Character) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {
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
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addItems(newItems: List<Character>) {
        val startPosition = list.size
        list.addAll(newItems)
        notifyItemRangeInserted(startPosition, newItems.size)
    }

}