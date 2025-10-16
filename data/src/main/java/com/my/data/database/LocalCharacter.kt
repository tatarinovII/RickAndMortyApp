package com.my.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class LocalCharacter(
    @PrimaryKey @ColumnInfo("id") val id: Int,
    @ColumnInfo("page") val page: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("status") val status: String,
    @ColumnInfo("species") val species: String,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("gender") val gender: String,
    @ColumnInfo("image") val image: String,
    @ColumnInfo("origin") val origin: String,
    @ColumnInfo("location") val location: String
)