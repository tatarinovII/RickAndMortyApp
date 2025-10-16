package com.my.domain.usecases

import com.my.domain.models.Character
import com.my.domain.repository.CharacterRepository

class GetNextPageUseCase(private val characterRepository: CharacterRepository) {
    suspend fun execute(page: Int, filters: Map<String, String>): List<Character> {
        return characterRepository.getPage(page, filters)
    }
}