package com.trile.hometest.store.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.trile.hometest.store.room.dao.ChipDao
import com.trile.hometest.store.room.entity.ChipEntity

/**
 *
 * @author lmtri
 * @since Aug 06, 2019 at 5:49 PM
 */
@Database(entities = [ChipEntity::class], exportSchema = true, version = 1)
abstract class RoomDataSource : RoomDatabase() {
    abstract fun chipDao(): ChipDao

    companion object {
        private var INSTANCE: RoomDataSource? = null

        fun getInstance(context: Context): RoomDataSource? {
            if (INSTANCE == null) {
                synchronized(RoomDataSource::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDataSource::class.java,
                        "test.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}