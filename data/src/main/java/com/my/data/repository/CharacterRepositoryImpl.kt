package com.my.data.repository

import android.content.Context
import android.util.Log
import com.my.data.database.MainDB
import com.my.data.mappers.Mapper
import com.my.data.retrofit.RetrofitObj
import com.my.domain.models.Character
import com.my.domain.repository.CharacterRepository
import java.io.IOException

class CharacterRepositoryImpl(context: Context) : CharacterRepository {

    private val mapper = Mapper()
    private val apiService = RetrofitObj.retrofit
    private val db = MainDB.getDb(context)


    override suspend fun getPage(
        page: Int, filters: Map<String, String>
    ): List<Character> {
        try {
            if (isPageInCache(page, filters)) {
                Log.i("!!!", "Page $page found in cache. Getting from DB.")
                val characterList = db.getDao().getCharactersByPage(page)
                return characterList.map {
                    mapper.mapLocalCharacterToCharacter(it)
                }
            } else {
                Log.i("!!!", "Page $page not in cache. Getting from network.")
                val response = apiService.getFilteredCharacters(
                    page, filters["status"], filters["species"], filters["type"], filters["gender"]
                )

                if (response.isSuccessful) {
                    val characterResponse = response.body()
                    if (characterResponse != null) {
                        Log.i("!!!", "Saving page $page to cache.")
                        characterResponse.results.forEach {
                            db.getDao().insertCharacter(
                                mapper.mapCharacterResponseToLocalCharacter(it, page)
                            )
                        }
                        return characterResponse.results.map {
                            mapper.mapCharacterResponseToCharacter(it)
                        }
                    }
                } else {
                    Log.e("!!!", "Network request failed with code: ${response.code()}")
                }
            }
        } catch (e: IOException) {
            Log.e("!!!", "Network error while getting page $page", e)
        } catch (e: Exception) {
            Log.e("!!!", "An unexpected error occurred in getPage", e)
        }
        return emptyList()
    }

    private suspend fun isPageInCache(page: Int, filters: Map<String, String>): Boolean {
        if (filters.isNotEmpty()) return false
        try {
            Log.i("!!!", "Checking page in cache")
            if (db.getDao().getCharactersOnPageCount(page) == 20) return true else false
        } catch (e: Exception) {
            Log.e("!!!", "Error while checking page in cache", e)
            throw e
        }
        return false
    }
}