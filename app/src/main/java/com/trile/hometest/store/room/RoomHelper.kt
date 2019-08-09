package com.trile.hometest.store.room

import com.trile.hometest.store.room.entity.ChipEntity

/**
 *
 * @author lmtri
 * @since Aug 07, 2019 at 4:04 PM
 */
class RoomHelper(database: RoomDataSource?) {
    private val mDao = database?.chipDao()

    fun getAllChipItems(): List<ChipEntity>? = mDao?.getAllChipItems()

    fun insertChipItems(items: List<ChipEntity>) = mDao?.insertChipItems(items)

//    fun getAllChipItems(): Single<List<ChipEntity>> =
//            Single.defer { Single.just(mDao?.getAllChipItems()) }
//
//    fun insertChipItems(items: List<ChipEntity>) =
//            Completable.defer {
//                mDao?.insertChipItems(items)
//                Completable.complete()
//            }
}