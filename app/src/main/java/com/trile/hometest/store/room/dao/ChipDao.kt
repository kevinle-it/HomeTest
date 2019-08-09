package com.trile.hometest.store.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trile.hometest.store.room.entity.ChipEntity

/**
 *
 * @author lmtri
 * @since Aug 07, 2019 at 3:27 PM
 */
@Dao
interface ChipDao {
    @Query("SELECT * FROM chip")
    fun getAllChipItems(): List<ChipEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChipItems(items: List<ChipEntity>)
}