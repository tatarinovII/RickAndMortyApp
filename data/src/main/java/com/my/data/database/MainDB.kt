package com.my.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalCharacter::class], version = 1)
abstract class MainDB: RoomDatabase() {

    abstract fun getDao(): CharacterDao

    companion object {
        fun getDb(context: Context): MainDB {
            return Room.databaseBuilder(
                context.applicationContext, MainDB::class.java, "rick_and_morty.db"
            ).build()
        }
    }
}