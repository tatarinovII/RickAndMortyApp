package com.my.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacter(character: LocalCharacter)

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): LocalCharacter?

    @Query("SELECT COUNT(*) FROM characters WHERE page = :page")
    suspend fun getCharactersOnPageCount(page: Int): Int

    @Query("SELECT * FROM characters WHERE page = :page")
    suspend fun getCharactersByPage(page: Int): List<LocalCharacter>
}
