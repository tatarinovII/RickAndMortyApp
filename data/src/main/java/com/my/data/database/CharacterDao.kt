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

    @Query(
        """
        SELECT * FROM characters WHERE
            (:name IS NULL OR name LIKE '%' || :name || '%') AND
            (:status IS NULL OR status = :status) AND
            (:gender IS NULL OR gender = :gender)
    """
    )
    suspend fun getFilteredCharactersFromDb(
        name: String?, status: String?, gender: String?
    ): List<LocalCharacter>
}
