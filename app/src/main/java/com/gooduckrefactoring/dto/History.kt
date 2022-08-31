package com.gooduckrefactoring.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History (
    @PrimaryKey(autoGenerate = true) val uid : Int?,
    @ColumnInfo(name = "keyword")val keyword : String? = null
)

