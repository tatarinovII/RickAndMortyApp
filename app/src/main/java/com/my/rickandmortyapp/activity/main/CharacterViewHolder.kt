package com.my.rickandmortyapp.activity.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.my.domain.models.Character
import com.my.rickandmortyapp.R
import com.my.rickandmortyapp.databinding.CharacterItemBinding

class CharacterViewHolder(
    item: View,
    private val onItemClick: (character: Character) -> Unit
): RecyclerView.ViewHolder(item) {

    private val binding = CharacterItemBinding.bind(item)

    fun bind(character: Character) {
        binding.tvName.text = character.name
        binding.tvGender.text = character.gender
        binding.tvSpecies.text = character.species
        Glide.with(itemView.context)
            .load(character.image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .fitCenter()
            .into(binding.ivCharacter)
        binding.cardView.setOnClickListener {
            onItemClick(character)
        }
    }

}
