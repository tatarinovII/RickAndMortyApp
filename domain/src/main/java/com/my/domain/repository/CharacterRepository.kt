package com.my.domain.repository

import com.my.domain.models.Character
import javax.naming.Context

interface CharacterRepository {
    suspend fun getPage(page: Int, filters: Map<String, String>): List<Character>
}