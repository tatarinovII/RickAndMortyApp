package com.my.data.repository

import android.content.Context
import android.util.Log
import com.my.data.database.MainDB
import com.my.data.mappers.Mapper
import com.my.data.retrofit.RetrofitObj
import com.my.data.utils.NetworkChecker
import com.my.domain.models.Character
import com.my.domain.repository.CharacterRepository

class CharacterRepositoryImpl(context: Context) : CharacterRepository {

    private val mapper = Mapper()
    private val apiService = RetrofitObj.retrofit
    private val db = MainDB.getDb(context)
    private val networkChecker = NetworkChecker(context)

    override suspend fun getPage(
        page: Int, filters: Map<String, String>
    ): List<Character> {
        try {
            if (networkChecker.isNetworkAvailable()) {
                Log.i("!!!", "Network available. Getting from API")
                return getFromApi(page, filters)
            } else {
                Log.i("!!!", "Network not available. Getting from DB")
                return getFromDb(filters)
            }
        } catch (e: Exception) {
            Log.e("!!!", "Error while getting page $page: ${e.message}")
            return emptyList()
        }
    }

    private suspend fun getFromApi(page: Int, filters: Map<String, String>): List<Character> {

        if (filters.isEmpty() && db.getDao().getCharactersOnPageCount(page) > 0) {
            Log.i("!!!", "Page $page in cache. Getting from db")
            return db.getDao().getCharactersByPage(page).map {
                mapper.mapLocalCharacterToCharacter(it)
            }
        }

        Log.i("!!!", "Page $page not in cache or filters applied. Getting from network.")
        val response = apiService.getFilteredCharacters(
            page = page,
            name = filters["name"],
            status = filters["status"],
            gender = filters["gender"]
        )
        if (response.isSuccessful) {
            val characterResponse = response.body()
            if (characterResponse != null) {
                if (filters.isEmpty()) {
                    Log.i("!!!", "Saving page $page to db")
                    characterResponse.results.forEach {
                        db.getDao()
                            .insertCharacter(mapper.mapCharacterResponseToLocalCharacter(it, page))
                    }
                }
                return characterResponse.results.map {
                    mapper.mapCharacterResponseToCharacter(it)
                }
            }
        } else {
            Log.e("!!!", "Network request failed. Code: ${response.code()}")
        }
        return emptyList()
    }

    private suspend fun getFromDb(filters: Map<String, String>): List<Character> {
        val characterList = db.getDao().getFilteredCharactersFromDb(
            name = filters["name"], status = filters["status"], gender = filters["gender"]
        )
        return characterList.map { mapper.mapLocalCharacterToCharacter(it) }
    }
}