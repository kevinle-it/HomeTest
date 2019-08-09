package com.trile.hometest.store.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 *
 * @author lmtri
 * @since Aug 07, 2019 at 3:25 PM
 */
@Entity(tableName = "chip", indices = [Index(value = ["name"], unique = true)])
data class ChipEntity(
        @ColumnInfo(name = "name")
        var name: String?
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}